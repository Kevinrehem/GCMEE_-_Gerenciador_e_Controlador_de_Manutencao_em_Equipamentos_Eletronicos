import { useEffect, useState } from 'react';
import { TechnicianApi } from '@/services/api';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableHeader, TableBody, TableRow, TableHead, TableCell } from '@/components/ui/table';

export default function TechniciansList() {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState({ id: undefined, name: '' });
  const [loading, setLoading] = useState(false);

  async function load() {
    setLoading(true);
    try {
      const data = await TechnicianApi.list();
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
        await TechnicianApi.update(form);
      } else {
        await TechnicianApi.create(form);
      }
      setForm({ id: undefined, name: '' });
      await load();
    } catch (e) {
      console.error(e);
      alert(e.message);
    }
  }

  async function remove(id) {
    if (!confirm('Confirmar exclusão?')) return;
    try {
      await TechnicianApi.remove(id);
      await load();
    } catch (e) {
      console.error(e);
      alert(e.message);
    }
  }

  return (
    <div className="w-full py-8 spacious">
      <div className="mb-6 flex items-center justify-between px-4">
        <h2 className="text-2xl font-semibold">Technicians</h2>
        <Button onClick={() => setForm({ id: undefined, name: '' })}>Novo</Button>
      </div>
      <div className="mb-6 flex justify-left px-4">
        <div className="w-1/2 md:w-1/2 grid gap-6">
          <Input
            placeholder="Name"
            value={form.name}
            onChange={(e) => setForm({ ...form, name: e.target.value })}
          />
          <div className="flex gap-2">
            <Button onClick={save}>{form.id ? 'Atualizar' : 'Criar'}</Button>
            {form.id && (
              <Button variant="secondary" onClick={() => setForm({ id: undefined, name: '' })}>
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
              <TableHead>Ações</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {loading && (
              <TableRow><TableCell colSpan={3}>Carregando...</TableCell></TableRow>
            )}
            {items.map((c) => (
              <TableRow key={c.id}>
                <TableCell>{c.id}</TableCell>
                <TableCell>{c.name}</TableCell>
                <TableCell>
                  <div className="flex gap-2">
                    <Button variant="outline" onClick={() => setForm({ id: c.id, name: c.name ?? '' })}>Editar</Button>
                    <Button variant="destructive" onClick={() => remove(c.id)}>Excluir</Button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
            {!loading && items.length === 0 && (
              <TableRow><TableCell colSpan={3}>Nenhum registro</TableCell></TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
