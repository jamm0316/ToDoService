const StickyFooter = ({ children }) => {
  return (
    <div className="fixed bottom-0 left-0 right-0 bg-white border-t border-gray-200 p-6">
      <div className="max-w-md mx-auto">{children}</div>
    </div>
  );
};

export default StickyFooter;