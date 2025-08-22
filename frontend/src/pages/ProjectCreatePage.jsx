import { useNavigate } from 'react-router-dom';
import PageHeader from '/src/components/layout/PageHeader.jsx';
import ProjectForm from '/src/components/features/projects/ProjectForm.jsx';
import useProjectForm from "/src/hooks/project/useProjectForm.jsx";
import useCreateProject from "/src/hooks/project/useCreateProject.jsx";

const ProjectCreatePage = () => {
  const navigate = useNavigate();
  const form = useProjectForm();
  const { createProject, loading, error } = useCreateProject()

  const handleSubmit = async (payload) => {
    console.log('submit', payload);
    const result = await createProject(payload);
    if (result.success) {
      navigate('/');
    } else {
      console.error('프로젝트 생성 실패'.result.error);
    }
  };

  return (
    <div className="min-h-screen bg-gray-50">
      <div className="max-w-md mx-auto bg-white min-h-screen">
        <PageHeader onBack={() => navigate(-1)} />
        <ProjectForm form={form} onSubmit={handleSubmit} loading={loading} />
      </div>
    </div>
  );
};

export default ProjectCreatePage;