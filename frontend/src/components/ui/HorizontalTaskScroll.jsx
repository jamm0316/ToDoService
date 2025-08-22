import React from 'react';
import TaskCard from "/src/components/features/task/TaskCard.jsx";
import {ChevronRight, SquarePlus} from 'lucide-react';
import {useNavigate} from "react-router-dom";
import {ROUTES} from "/src/router/routes.js";
import {useDragScroll} from "/src/hooks/useDragScroll.jsx";

const HorizontalTaskScroll = ({tasks, title}) => {
  const navigate = useNavigate();
  const { dragScrollProps, currentScrollIndex, isDragging } = useDragScroll();
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
      <div
        {...dragScrollProps}
        className="overflow-x-auto pb-4"
      >
        <style jsx>{`
          /* Chrome, Safari, Opera */
          div::-webkit-scrollbar {
            display: none;
          }
        `}</style>
        <div className="flex">
          {tasks.map((task) => (
            <TaskCard
              key={task.id}
              task={task}
              isCompact
              style={{ pointerEvents: isDragging ? 'none' : 'auto' }}
            />
          ))}
        </div>
      </div>


      {/* 스크롤 인디케이터 */}
      {tasks.length > 1 && (
        <div className="flex justify-center mt-4">
          <div className="flex space-x-2">
            {tasks.map((_, index) => (
              <div
                key={index}
                className={`w-2 h-2 rounded-full transition-colors duration-200 ${
                  index === currentScrollIndex ? 'bg-blue-600' : 'bg-gray-300'
                }`}
              />
            ))}
          </div>
        </div>
      )}
    </section>
  );
};

export default HorizontalTaskScroll;