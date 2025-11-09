import { Link, useLocation } from 'react-router-dom';

const links = [
  { to: '/', label: 'Home' },
  { to: '/customers', label: 'Customers' },
  { to: '/equipment', label: 'Equipment' },
  { to: '/procedures', label: 'Procedures' },
  { to: '/technicians', label: 'Technicians' },
  { to: '/service-orders', label: 'Service Orders' },
];

export default function NavBar() {
  const { pathname } = useLocation();
  return (
    <header className="border-b bg-background">
      <div className="w-full bg-background py-2">
        <div className="container mx-auto">
          <nav className="flex w-full">
            {links.map((l) => (
              <Link
                key={l.to}
                to={l.to}
                className={`flex-1 text-center px-4 py-2 rounded-md border ${pathname === l.to ? 'bg-primary text-primary-foreground' : 'hover:bg-muted'}`}
              >
                {l.label}
              </Link>
            ))}
          </nav>
        </div>
      </div>
    </header>
  );
}
