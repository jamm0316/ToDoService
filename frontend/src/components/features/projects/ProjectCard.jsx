import React from 'react';
import {User} from 'lucide-react';

const ProjectCard = ({project, isCompact = false}) => {
  return (
    <div
      className={`${project.color} rounded-2xl p-5 text-white shadow-lg ${
        isCompact ? 'min-w-[280px] mr-4' : 'min-h-[200px]'
      } flex flex-col justify-between transition-transform hover:scale-105 cursor-pointer`}
    >
      <div className="flex items-center justify-between mb-4">
        <div className="w-8 h-8 bg-white/20 rounded-full flex items-center justify-center">
          <User className="w-4 h-4" />
        </div>
        <span className="text-sm opacity-90 font-medium">{project.type}</span>
      </div>

      <div className="flex-1">
        <h3 className="text-lg font-bold mb-2 leading-tight">{project.title}</h3>
        <p className="text-sm opacity-80 mb-4">{project.date}</p>

        <div className="flex items-center justify-between">
          <span className="text-sm opacity-90">Progress</span>
          <span className="text-sm font-medium">{project.progress}%</span>
        </div>
        <div className="w-full bg-white/20 rounded-full h-2 mt-2">
          <div
            className="bg-white rounded-full h-2 transition-all duration-300"
            style={{ width: `${project.progress}%` }}
          />
        </div>
      </div>
    </div>
  );
};

export default ProjectCard;