import { useEffect, useState } from 'react';
import { CustomerApi } from '@/services/api';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableHeader, TableBody, TableRow, TableHead, TableCell } from '@/components/ui/table';

export default function CustomersList() {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState({ id: undefined, name: '', email: '', phoneNumber: '' });
  const [loading, setLoading] = useState(false);

  async function load() {
    setLoading(true);
    try {
      const data = await CustomerApi.list();
      setItems(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error(e);
      alert(e.message);
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => { load(); }, []);

  async function save() {
    try {
      if (form.id) {
        await CustomerApi.update(form);
      } else {
        await CustomerApi.create(form);
      }
      setForm({ id: undefined, name: '', email: '' });
      await load();
    } catch (e) {
      console.error(e);
      alert(e.message);
    }
  }

  async function remove(id) {
    if (!confirm('Confirmar exclusão?')) return;
    try {
      await CustomerApi.remove(id);
      await load();
    } catch (e) {
      console.error(e);
      alert(e.message);
    }
  }

  return (
    <div className="w-full py-8 spacious">
      <div className="mb-6 flex items-center justify-between px-4">
        <h2 className="text-2xl font-semibold">Customers</h2>
        <Button onClick={() => setForm({ id: undefined, name: '', email: '' })}>Novo</Button>
      </div>
      <div className="mb-6 flex justify-left px-4">
        <div className="w-1/2 md:w-1/2 grid gap-6">
          <Input
            placeholder="Name"
            value={form.name}
            onChange={(e) => setForm({ ...form, name: e.target.value })}
          />
          <Input
            placeholder="Email"
            value={form.email}
            onChange={(e) => setForm({ ...form, email: e.target.value })}
          />
          <Input
            placeholder="Phone number"
            value={form.phoneNumber}
            onChange={(e) => setForm({ ...form, phoneNumber: e.target.value })}
          />
          <div className="flex gap-2">
            <Button onClick={save}>{form.id ? 'Atualizar' : 'Criar'}</Button>
            {form.id && (
              <Button variant="secondary" onClick={() => setForm({ id: undefined, name: '', email: '', phoneNumber: '' })}>
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
              <TableHead>Email</TableHead>
              <TableHead>Phone</TableHead>
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
                <TableCell>{c.email}</TableCell>
                <TableCell>{c.phoneNumber}</TableCell>
                <TableCell>
                  <div className="flex gap-2">
                    <Button variant="outline" onClick={() => setForm({ id: c.id, name: c.name ?? '', email: c.email ?? '' })}>Editar</Button>
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
