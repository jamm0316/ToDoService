import React, {useState} from 'react';
import TaskCard from "/src/components/features/task/TaskCard.jsx";
import {ChevronRight, SquarePlus} from 'lucide-react';
import {useDragScroll} from "/src/hooks/useDragScroll.jsx";
import TaskDetailModal from "/src/components/features/task/TaskDetailModal.jsx";

const HorizontalTaskScroll = ({tasks, title, onUpdate}) => {
  const [selectedTaskId, setSelectedTaskId] = useState(null);
  const [isModalOpen, setIsModalOpen] = useState(false)
  const { dragScrollProps, currentScrollIndex, isDragging } = useDragScroll(tasks.length);
  const handleTaskClick = (taskId) => {
    // 드래그 중일 때는 클릭 이벤트 무시
    if (isDragging) return;

    setSelectedTaskId(taskId);
    setIsModalOpen(true);
  };

  const handleModalClose = () => {
    setIsModalOpen(false);
    setSelectedTaskId(null);
  };

  const handleTaskUpdate = () => {
    // 프로젝트 목록 새로고침
    if (onUpdate) {
      onUpdate();
    }
  };

  return (
    <>
      <section className="mb-8">
        <div className="flex items-center justify-between mb-4">
          <h2 className="text-xl font-bold text-gray-800">{title}</h2>
          <div className="flex items-center space-x-2">
            <ChevronRight className="w-5 h-5 text-gray-400"/>
          </div>
        </div>

        {/* 가로 스크롤 컨테이너 */}
        <div
          {...dragScrollProps}
          className="overflow-x-auto pb-4"
        >
          <style>{`
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
                onClick={handleTaskClick}
                isCompact
                style={{pointerEvents: isDragging ? 'none' : 'auto'}}
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

      {/* Project Detail Modal */}
      <TaskDetailModal
        taskId={selectedTaskId}
        isOpen={isModalOpen}
        onClose={handleModalClose}
        onUpdate={handleTaskUpdate}
      />
    </>
  );
};

export default HorizontalTaskScroll;