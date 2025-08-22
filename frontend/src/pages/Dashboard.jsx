import React, {useMemo, useState} from 'react';
import SearchBar from "/src/components/ui/SearchBar.jsx";
import TabSelector from "/src/components/layout/TabSelector.jsx";
import HorizontalProjectScroll from "/src/components/ui/HorizontalProjectScroll.jsx";
import RecentActivityItem from "/src/components/features/RecentActivityItem.jsx";
import Header from "/src/components/layout/Header.jsx";
import BottomNav from "/src/components/layout/BottomNav.jsx";
import useSummaryProject from "/src/hooks/useSummaryProject.jsx";
import {mockRecentActivity} from "/src/data/mockProjects.jsx";

const colorMap = {
  1: 'bg-gradient-to-br from-blue-600 to-purple-700',
  2: 'bg-gradient-to-br from-blue-500 to-cyan-500',
  3: 'bg-gradient-to-br from-purple-600 to-pink-600',
  4: 'bg-gradient-to-br from-green-500 to-teal-600',
  5: 'bg-gradient-to-br from-orange-500 to-amber-600',
  6: 'bg-gradient-to-br from-pink-500 to-rose-600',
};

const Dashboard = () => {
  const [activeTab, setActiveTab] = useState('today');
  const [activeNavTab, setActiveNavTab] = useState('home');
  const { data: projectData, loading: projectLoading, error: projectError} = useSummaryProject();
  const handleSearch = (query) => {
    console.log('Searching for:', query);
  };


  const projectsForCard = useMemo(() => {
    const list = Array.isArray(projectData) ? projectData : [];

    return list.map((p) => ({
      id: p.id,
      title: p.name,
      type: p.visibility,
      date: `${p.startDate} ~ ${p.endDate}`,
      color: colorMap[p.colorId] ?? 'bg-gradient-to-br from-slate-500 to-slate-700',
      progress:
        typeof p.progress === 'number'
          ? (p.progress <= 1 ? Math.round(p.progress * 100) : Math.round(p.progress))
          : 0,
    }));
  }, [projectData]);

  // 현재 탭에 따라 표시할 콘텐츠 결정
  const renderMainContent = () => {
    if (activeTab === 'projects') {
      return (
        <>
          {projectLoading && <div className="py-6 text-gray-500">프로젝트 불러오는 중…</div>}
          {projectError && <div className="py-6 text-red-500">에러: {String(projectError)}</div>}
          {!projectLoading && !projectError && (
            <HorizontalProjectScroll
              projects={projectsForCard}
              title="Active Projects"
            />
          )}
        </>
      );
    }

    // activeTab === 'today' 또는 기본값
    return (
      <div className="py-6 text-gray-500">
        오늘 할 일 내용이 여기에 표시됩니다.
      </div>
    );
  };

  return (
    <div>
      <Header />
      {/* 메인 콘텐츠 */}
      <main className="px-6 pb-24">
        <SearchBar placeholder="Search" onSearch={handleSearch}/>

        <TabSelector activeTab={activeTab} onTabChange={setActiveTab}/>

        {/* 탭에 따른 동적 콘텐츠 */}
        {renderMainContent()}

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
      <BottomNav activeTab="home" onTabChange={() => {}} />
    </div>
  );
};

export default Dashboard;