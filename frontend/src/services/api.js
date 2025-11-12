const API_BASE = import.meta.env.VITE_API_BASE || 'http://localhost:8081';

async function http(path, { method = 'GET', data, headers } = {}) {
  const opts = { method, headers: { 'Content-Type': 'application/json', ...(headers || {}) } };
  if (data !== undefined) opts.body = JSON.stringify(data);
  const res = await fetch(`${API_BASE}${path}`, opts);
  const isJson = (res.headers.get('content-type') || '').includes('application/json');
  const payload = isJson ? await res.json().catch(() => null) : await res.text();
  if (!res.ok) {
    throw new Error(typeof payload === 'string' ? payload : (payload?.message || 'Erro na requisição'));
  }
  return payload;
}

export const CustomerApi = {
  list: () => http('/app/customer/select-all'),
  create: (dto) => http('/app/customer/create', { method: 'POST', data: dto }),
  update: (dto) => http('/app/customer/update', { method: 'PUT', data: dto }),
  remove: (id) => http(`/app/customer/delete/${id}`, { method: 'DELETE' }),
};

export const EquipmentApi = {
  list: () => http('/app/equipment/select-all'),
  create: (dto) => http('/app/equipment/create', { method: 'POST', data: dto }),
  update: (dto) => http('/app/equipment/update', { method: 'PUT', data: dto }),
  remove: (id) => http(`/app/equipment/delete/${id}`, { method: 'DELETE' }),
};

export const ProcedureApi = {
  list: () => http('/app/procedure/select-all'),
  create: (dto) => http('/app/procedure/create', { method: 'POST', data: dto }),
  update: (dto) => http('/app/procedure/update', { method: 'PUT', data: dto }),
  remove: (id) => http(`/app/procedure/delete/${id}`, { method: 'DELETE' }),
};

export const TechnicianApi = {
  list: () => http('/app/technician/select-all'),
  create: (dto) => http('/app/technician/create', { method: 'POST', data: dto }),
  update: (dto) => http('/app/technician/update', { method: 'PUT', data: dto }),
  remove: (id) => http(`/app/technician/delete/${id}`, { method: 'DELETE' }),
};

export const ServiceOrderApi = {
  list: () => http('/app/service-order/select-all'),
  create: (dto) => http('/app/service-order/create', { method: 'POST', data: dto }),
  update: (dto) => http('/app/service-order/update', { method: 'PUT', data: dto }),
  remove: (id) => http(`/app/service-order/delete/${id}`, { method: 'DELETE' }),
};

export { API_BASE };
