import React, {useState} from 'react';
import SearchBar from "/src/components/ui/SearchBar.jsx";
import TabSelector from "/src/components/layout/TabSelector.jsx";
import HorizontalProjectScroll from "/src/components/ui/HorizontalProjectScroll.jsx";
import {mockProjects, mockRecentActivity} from "/src/data/mockProjects.jsx";
import RecentActivityItem from "/src/components/features/RecentActivityItem.jsx";

const Dashboard = () => {
  const [activeTab, setActiveTab] = useState('today');
  const [activeNavTab, setActiveNavTab] = useState('home');

  const handleSearch = (query) => {
    console.log('Searching for:', query);
  };

  return (
    <div>
      {/* 메인 콘텐츠 */}
      <main className="px-6 pb-24">
        <SearchBar placeholder="Search" onSearch={handleSearch}/>

        <TabSelector activeTab={activeTab} onTabChange={setActiveTab}/>

        {/* 가로 스크롤 프로젝트 카드 */}
        <HorizontalProjectScroll
          projects={mockProjects}
          title="Active Projects"
        />

        {/* 최근 활동 섹션 */}
        <section>
          <div className="flex items-center justify-between mb-4">
            <h2 className="text-xl font-bold text-gray-800">지난주 회고</h2>
            <button className="text-blue-600 text-sm font-medium hover:text-blue-700">
              View All
            </button>
          </div>
          <div className="space-y-3">
            {mockRecentActivity.map((activity) => (
              <RecentActivityItem key={activity.id} activity={activity}/>
            ))}
          </div>
        </section>
      </main>

    </div>
  );
};

export default Dashboard;