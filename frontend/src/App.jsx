import { Routes, Route } from 'react-router-dom'
import NavBar from '@/components/layout/NavBar'
import Home from '@/pages/Home'
import CustomersList from '@/pages/customers/CustomersList'
import EquipmentList from '@/pages/equipments/EquipmentList'
import ProceduresList from '@/pages/procedures/ProceduresList'
import TechniciansList from '@/pages/technicians/TechniciansList'
import ServiceOrdersList from '@/pages/serviceOrders/ServiceOrdersList'

function App() {
  return (
    <div className="min-h-screen">
      <NavBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/customers" element={<CustomersList />} />
        <Route path="/equipment" element={<EquipmentList />} />
        <Route path="/procedures" element={<ProceduresList />} />
        <Route path="/technicians" element={<TechniciansList />} />
        <Route path="/service-orders" element={<ServiceOrdersList />} />
      </Routes>
    </div>
  )
}

export default App