import { useNavigate } from 'react-router-dom';
import PageHeader from '/src/components/layout/project/PageHeader.jsx';
import ProjectForm from '/src/components/features/projects/ProjectForm.jsx';
import useProjectForm from "/src/hooks/useProjectForm.jsx";

const ProjectCreatePage = () => {
  const navigate = useNavigate();
  const form = useProjectForm();

  const handleSubmit = (payload) => {
    console.log('submit', payload);
    // TODO: API 호출
    navigate('/');
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-md mx-auto bg-white min-h-screen">
        <PageHeader onBack={() => navigate(-1)} />
        <ProjectForm form={form} onSubmit={handleSubmit} />
      </div>
    </div>
  );
};

export default ProjectCreatePage;