import { Link } from 'react-router-dom';
import { Button } from '@/components/ui/button';

export default function Home() {
  return (
    // Removi a classe "spacious" que não é padrão do Tailwind
    <div className="container mx-auto py-10">
      <h1 className="text-3xl font-bold">Dashboard</h1>
      <p className="text-muted-foreground mt-2">Gerencie as entidades do sistema.</p>

      {/* 1. Alterado o 'space-y-10' (40px) para 'space-y-[5px]'
      */}
      <div className="mt-10 flex flex-col items-center space-y-[5px]">

        {/* 2. Alterado 'w-160' para 'w-[100px]'
          3. Alterado 'h-28' para 'h-[30px]'
          4. Classes de alinhamento e 'whitespace' removidas (o Button já faz isso)
          5. Classe do Link removida (o 'asChild' cuida da estilização)
        */}
        <Button asChild className="w-[160px] h-[30px]">
          <Link to="/customers">Clientes</Link>
        </Button>

        <Button asChild className="w-[160px] h-[30px]">
          <Link to="/equipment">Equipmentos</Link>
        </Button>

        <Button asChild className="w-[160px] h-[30px]">
          <Link to="/procedures">Serviços</Link>
        </Button>

        <Button asChild className="w-[160px] h-[30px]">
          <Link to="/technicians">Técnicos</Link>
        </Button>

        <Button asChild className="w-[160px] h-[30px]">
          <Link to="/service-orders">Ordens de Serviço</Link>
        </Button>
      </div>
    </div>
  );
}