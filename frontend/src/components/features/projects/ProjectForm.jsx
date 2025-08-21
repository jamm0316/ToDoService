import { projectColors, statusOptions, visibilityOptions } from '/src/data/project/constants.jsx';
import ColorPicker from '/src/components/ui/project/ColorPicker.jsx';
import TextInput from '/src/components/ui/project/TextInput.jsx';
import DateRangePicker from '/src/components/ui/project/DateRangePicker.jsx';
import SelectField from '/src/components/ui/project/SelectField.jsx';
import VisibilityOptionCard from '/src/components/ui/project/VisibilityOptionCard.jsx';
import StickyFooter from '/src/components/ui/project/StickyFooter.jsx';

const ProjectForm = ({ form, onSubmit }) => {
  const { formData, errors, setField, validate } = form;

  const handleSubmit = () => {
    if (!validate()) return;
    const payload = {
      colorId: formData.colorId,
      name: formData.name,
      status: formData.status,
      period: { startDate: formData.startDate, endDate: formData.endDate },
      description: formData.description || null,
      isPublic: formData.isPublic,
      visibility: formData.visibility,
    };
    onSubmit?.(payload);
  };

  return (
    <>
      <main className="p-6 pb-24">
        <ColorPicker
          colors={projectColors}
          value={formData.colorId}
          onChange={(id) => setField('colorId', id)}
        />

        <TextInput
          label="Title"
          value={formData.name}
          onChange={(v) => setField('name', v)}
          placeholder="UI Design"
          error={errors.name}
        />

        <DateRangePicker
          start={formData.startDate}
          end={formData.endDate}
          onChangeStart={(v) => setField('startDate', v)}
          onChangeEnd={(v) => setField('endDate', v)}
          errors={errors}
        />

        <div className="mb-8">
          <label className="block text-sm text-gray-500 mb-3 uppercase tracking-wide">Description</label>
          <textarea
            value={formData.description}
            onChange={(e) => setField('description', e.target.value)}
            placeholder="Lorem Ipsum is simply dummy text..."
            className="w-full text-gray-700 resize-none border-none outline-none bg-transparent h-24 text-lg leading-relaxed placeholder-gray-400"
          />
        </div>

        <SelectField
          label="Status"
          value={formData.status}
          onChange={(v) => setField('status', v)}
          options={statusOptions}
        />

        <div className="mb-8">
          <label className="block text-sm text-gray-500 mb-4 uppercase tracking-wide">Visibility</label>
          <div className="space-y-3">
            {visibilityOptions.map(opt => (
              <VisibilityOptionCard
                key={opt.value}
                active={formData.visibility === opt.value}
                label={opt.label}
                description={opt.description}
                onClick={() => {
                  setField('visibility', opt.value);
                  setField('isPublic', opt.value === 'PUBLIC');
                }}
              />
            ))}
          </div>
        </div>
      </main>

      <StickyFooter>
        <button
          onClick={handleSubmit}
          className="w-full bg-gradient-to-r from-blue-600 to-purple-600 text-white py-4 rounded-2xl font-bold text-lg hover:shadow-xl transition-shadow active:scale-95"
        >
          Create New Task
        </button>
      </StickyFooter>
    </>
  );
};

export default ProjectForm;