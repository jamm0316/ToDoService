import React from 'react';
import TaskCard from "/src/components/features/task/TaskCard.jsx";
import {ChevronRight, SquarePlus} from 'lucide-react';
import {useNavigate} from "react-router-dom";
import {ROUTES} from "/src/router/routes.js";

const HorizontalTaskScroll = ({tasks, title}) => {
  const navigate = useNavigate();

  return (
    <section className="mb-8">
      <div className="flex items-center justify-between mb-4">
        <h2 className="text-xl font-bold text-gray-800">{title}</h2>
        <div className="flex items-center space-x-2">
          <SquarePlus
            className="w-5 h-5 text-gray-400 cursor-pointer hover:text-gray-600"
            onClick={() => navigate(ROUTES.TASK.CREATE)}
          />
          <ChevronRight className="w-5 h-5 text-gray-400"/>
        </div>
      </div>

      {/* 가로 스크롤 컨테이너 */}
      <div className="overflow-x-auto pb-4">
        <div className="flex">
          {tasks.map((task) => (
            <TaskCard key={task.id} task={task} isCompact/>
          ))}
        </div>
      </div>

      {/* 스크롤 인디케이터 */}
      <div className="flex justify-center mt-4">
        <div className="flex space-x-2">
          {tasks.map((_, index) => (
            <div
              key={index}
              className={`w-2 h-2 rounded-full ${
                index === 0 ? 'bg-blue-600' : 'bg-gray-300'
              } transition-colors`}
            />
          ))}
        </div>
      </div>
    </section>
  );
};

export default HorizontalTaskScroll;