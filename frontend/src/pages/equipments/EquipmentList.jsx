import { useEffect, useState } from 'react';
import { EquipmentApi, CustomerApi } from '@/services/api';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableHeader, TableBody, TableRow, TableHead, TableCell } from '@/components/ui/table';

export default function EquipmentList() {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState({ id: undefined, name: '', customerId: undefined, equipType: 'LAPTOP' });
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(false);

  async function load() {
    setLoading(true);
    try {
      const data = await EquipmentApi.list();
      setItems(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error(e);
      alert(e.message);
    } finally {
      setLoading(false);
    }
  }

  async function loadCustomers() {
    try {
      const data = await CustomerApi.list();
      setCustomers(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error(e);
    }
  }

  useEffect(() => { load(); loadCustomers(); }, []);

  async function save() {
    try {
      if (form.id) {
        await EquipmentApi.update(form);
      } else {
        await EquipmentApi.create(form);
      }
      setForm({ id: undefined, name: '', serialNumber: '' });
      await load();
    } catch (e) {
      console.error(e);
      alert(e.message);
    }
  }

  async function remove(id) {
    if (!confirm('Confirmar exclusão?')) return;
    try {
      await EquipmentApi.remove(id);
      await load();
    } catch (e) {
      console.error(e);
      alert(e.message);
    }
  }

  return (
    <div className="w-full py-8 spacious">
      <div className="mb-6 flex items-center justify-between px-4">
        <h2 className="text-2xl font-semibold">Equipment</h2>
        <Button onClick={() => setForm({ id: undefined, name: '', serialNumber: '' })}>Novo</Button>
      </div>
      <div className="mb-6 flex justify-left px-4">
        <div className="w-1/2 md:w-1/2 grid gap-6">
          <Input
            placeholder="Name"
            value={form.name}
            onChange={(e) => setForm({ ...form, name: e.target.value })}
          />
          <select
            className="border rounded-md px-3 py-2 bg-white text-black"
            value={form.customerId ?? ''}
            onChange={(e) => setForm({ ...form, customerId: e.target.value ? Number(e.target.value) : undefined })}
          >
            <option value="">Selecione o cliente (owner)</option>
            {customers.map((c) => (
              <option key={c.id} value={c.id}>{c.name}</option>
            ))}
          </select>
          <select
            className="border rounded-md px-3 py-2 bg-white text-black"
            value={form.equipType}
            onChange={(e) => setForm({ ...form, equipType: e.target.value })}
          >
            <option value="LAPTOP">LAPTOP</option>
            <option value="DESKTOP">DESKTOP</option>
            <option value="MONITOR">MONITOR</option>
          </select>
          <div className="flex gap-2">
            <Button onClick={save}>{form.id ? 'Atualizar' : 'Criar'}</Button>
            {form.id && (
              <Button variant="secondary" onClick={() => setForm({ id: undefined, name: '', serialNumber: '' })}>
                Cancelar
              </Button>
            )}
          </div>
        </div>
      </div>

      <div className="w-full overflow-x-auto">
        <Table className="border table-shadow">
          <TableHeader>
            <TableRow className="bg-muted">
              <TableHead>ID</TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Type</TableHead>
              <TableHead>Owner</TableHead>
              <TableHead>Ações</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {loading && (
              <TableRow><TableCell colSpan={4}>Carregando...</TableCell></TableRow>
            )}
            {items.map((c) => (
              <TableRow key={c.id}>
                <TableCell>{c.id}</TableCell>
                <TableCell>{c.name}</TableCell>
                <TableCell>{c.equipType ?? c.type}</TableCell>
                <TableCell>{c.owner?.name}</TableCell>
                <TableCell>
                  <div className="flex gap-2">
                    <Button variant="outline" onClick={() => setForm({ id: c.id, name: c.name ?? '', serialNumber: c.serialNumber ?? '' })}>Editar</Button>
                    <Button variant="destructive" onClick={() => remove(c.id)}>Excluir</Button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
            {!loading && items.length === 0 && (
              <TableRow><TableCell colSpan={4}>Nenhum registro</TableCell></TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
