import { useNavigate } from 'react-router-dom';
import PageHeader from '/src/components/layout/PageHeader.jsx';
import TaskForm from '/src/components/features/task/TaskForm.jsx';
import useTaskForm from "/src/hooks/task/useTaskForm.jsx";
import useCreateTask from "/src/hooks/task/useCreateTask.jsx";

const TaskCreatePage = () => {
  const navigate = useNavigate();
  const form = useTaskForm();
  const { createTask, loading, error } = useCreateTask();

  const handleSubmit = async (payload) => {
    const result = await createTask(payload);
    if (result.success) {
      navigate('/');
    } else {
      console.error('태스크 생성 실패', result.error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-md mx-auto bg-white min-h-screen">
        <PageHeader onBack={() => navigate(-1)} />
        <TaskForm form={form} onSubmit={handleSubmit} loading={loading} />
      </div>
    </div>
  );
};

export default TaskCreatePage;