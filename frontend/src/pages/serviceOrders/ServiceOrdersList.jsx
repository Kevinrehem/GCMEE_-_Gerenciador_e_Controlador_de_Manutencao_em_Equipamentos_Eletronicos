import { useEffect, useState } from 'react';
import { ServiceOrderApi, TechnicianApi, EquipmentApi, ProcedureApi } from '@/services/api';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Table, TableHeader, TableBody, TableRow, TableHead, TableCell } from '@/components/ui/table';

export default function ServiceOrdersList() {
  const [items, setItems] = useState([]);
  const [form, setForm] = useState({ id: undefined, technicianId: undefined, equipmentId: undefined, procedureIds: [], serviceOrderStatus: 'ON_HOLD' });
  const [technicians, setTechnicians] = useState([]);
  const [equipments, setEquipments] = useState([]);
  const [procedures, setProcedures] = useState([]);
  const [loading, setLoading] = useState(false);

  async function load() {
    setLoading(true);
    try {
      const data = await ServiceOrderApi.list();
      setItems(Array.isArray(data) ? data : []);
    } catch (e) {
      console.error(e);
      alert(e.message);
    } finally {
      setLoading(false);
    }
  }

  async function loadReferences() {
    try {
      const t = await TechnicianApi.list();
      const e = await EquipmentApi.list();
      const p = await ProcedureApi.list();
      setTechnicians(Array.isArray(t) ? t : []);
      setEquipments(Array.isArray(e) ? e : []);
      setProcedures(Array.isArray(p) ? p : []);
    } catch (err) {
      console.error(err);
    }
  }

  useEffect(() => { load(); loadReferences(); }, []);

  async function save() {
    try {
      const dto = {
        id: form.id,
        technicianId: form.technicianId,
        equipmentId: form.equipmentId,
        procedureIds: form.procedureIds,
        serviceOrderStatus: form.serviceOrderStatus,
      };
      if (form.id) {
        await ServiceOrderApi.update(dto);
      } else {
        await ServiceOrderApi.create(dto);
      }
      setForm({ id: undefined, technicianId: undefined, equipmentId: undefined, procedureIds: [], serviceOrderStatus: 'ON_HOLD' });
      await load();
    } catch (e) {
      console.error(e);
      alert(e.message);
    }
  }

  async function remove(id) {
    if (!confirm('Confirmar exclusão?')) return;
    try {
      await ServiceOrderApi.remove(id);
      await load();
    } catch (e) {
      console.error(e);
      alert(e.message);
    }
  }

  return (
    <div className="w-full py-8 spacious">
      <div className="mb-6 flex items-center justify-between px-4">
        <h2 className="text-2xl font-semibold">Service Orders</h2>
        <Button onClick={() => setForm({ id: undefined, technicianId: undefined, equipmentId: undefined, procedureIds: [], serviceOrderStatus: 'ON_HOLD' })}>Novo</Button>
      </div>
      <div className="mb-6 flex justify-left px-4">
        <div className="w-1/2 md:w-1/2 grid gap-6">
          <div className="grid md:grid-cols-3 gap-6 items-start">
            <select
              className="border rounded-md px-3 py-2 bg-white text-black w-full"
              value={form.technicianId ?? ''}
              onChange={(e) => setForm({ ...form, technicianId: e.target.value ? Number(e.target.value) : undefined })}
            >
              <option value="">Selecione Técnico</option>
              {technicians.map(t => <option key={t.id} value={t.id}>{t.name}</option>)}
            </select>

            <select
              className="border rounded-md px-3 py-2 bg-white text-black w-full"
              value={form.equipmentId ?? ''}
              onChange={(e) => setForm({ ...form, equipmentId: e.target.value ? Number(e.target.value) : undefined })}
            >
              <option value="">Selecione Equipamento</option>
              {equipments.map(e => <option key={e.id} value={e.id}>{e.name}</option>)}
            </select>

            <div>
              <label className="block mb-2 text-sm">Procedimentos (selecione múltiplos)</label>
              <select
                multiple
                className="border rounded-md px-3 py-2 bg-white text-black h-36 w-full"
                value={form.procedureIds.map(String)}
                onChange={(e) => {
                  const options = Array.from(e.target.selectedOptions).map(opt => Number(opt.value));
                  setForm({ ...form, procedureIds: options });
                }}
              >
                {procedures.map(p => <option key={p.id} value={p.id}>{p.name} - {p.price}</option>)}
              </select>
            </div>
          </div>

          <div className="grid md:grid-cols-2 gap-6 items-end">
            <select
              className="border rounded-md px-3 py-2 bg-white text-black w-full"
              value={form.serviceOrderStatus}
              onChange={(e) => setForm({ ...form, serviceOrderStatus: e.target.value })}
            >
              <option value="ON_HOLD">ON_HOLD</option>
              <option value="IN_PROGRESS">IN_PROGRESS</option>
              <option value="AWAITING_PAYMENT">AWAITING_PAYMENT</option>
              <option value="PAID">PAID</option>
              <option value="CANCELLED">CANCELLED</option>
            </select>

            <div className="flex gap-2 justify-start md:justify-end">
              <Button onClick={save}>{form.id ? 'Atualizar' : 'Criar'}</Button>
              {form.id && (
                <Button variant="secondary" onClick={() => setForm({ id: undefined, technicianId: undefined, equipmentId: undefined, procedureIds: [], serviceOrderStatus: 'ON_HOLD' })}>
                  Cancelar
                </Button>
              )}
            </div>
          </div>
        </div>
      </div>

      <div className="w-full overflow-x-auto">
        <Table className="border table-shadow">
          <TableHeader>
            <TableRow className="bg-muted">
              <TableHead>ID</TableHead>
              <TableHead>Price</TableHead>
              <TableHead>Technician</TableHead>
              <TableHead>Equipment</TableHead>
              <TableHead>Procedures</TableHead>
              <TableHead>Status</TableHead>
              <TableHead>Ações</TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            {loading && (
              <TableRow><TableCell colSpan={7}>Carregando...</TableCell></TableRow>
            )}
            {items.map((c) => (
              <TableRow key={c.id}>
                <TableCell>{c.id}</TableCell>
                <TableCell>{c.price}</TableCell>
                <TableCell>{c.technician?.name}</TableCell>
                <TableCell>{c.equipment?.name}</TableCell>
                <TableCell>{(c.procedures || []).map(p => p.name).join(', ')}</TableCell>
                <TableCell>{c.serviceOrderStatus}</TableCell>
                <TableCell>
                  <div className="flex gap-2">
                    <Button variant="outline" onClick={() => setForm({ id: c.id, technicianId: c.technician?.id, equipmentId: c.equipment?.id, procedureIds: (c.procedures || []).map(p => p.id), serviceOrderStatus: c.serviceOrderStatus })}>Editar</Button>
                    <Button variant="destructive" onClick={() => remove(c.id)}>Excluir</Button>
                  </div>
                </TableCell>
              </TableRow>
            ))}
            {!loading && items.length === 0 && (
              <TableRow><TableCell colSpan={7}>Nenhum registro</TableCell></TableRow>
            )}
          </TableBody>
        </Table>
      </div>
    </div>
  );
}
