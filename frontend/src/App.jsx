import Header from "/src/components/layout/Header.jsx";
import BottomNav from "/src/components/layout/BottomNav.jsx";
import Dashboard from "/src/pages/Dashboard.jsx";

function App() {

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-md mx-auto bg-white min-h-screen relative">
        <Header />
        <main className="px-6 pb-24">
          <Dashboard />
        </main>
        <BottomNav activeTab="home" onTabChange={() => {}} />
      </div>
    </div>
  );
}

export default App
