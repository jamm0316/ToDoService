import { useRef, useState, useEffect, useCallback } from 'react';

export const useDragScroll = (itemCount = 0) => {
  const scrollContainerRef = useRef(null);
  const [isDragging, setIsDragging] = useState(false);
  const [startX, setStartX] = useState(0);
  const [scrollLeft, setScrollLeft] = useState(0);
  const [currentScrollIndex, setCurrentScrollIndex] = useState(0);

  // 드래그 시작
  const handleMouseDown = useCallback((e) => {
    setIsDragging(true);
    const container = scrollContainerRef.current;
    if (!container) return;

    setStartX(e.pageX - container.offsetLeft);
    setScrollLeft(container.scrollLeft);
    container.style.cursor = 'grabbing';
  }, []);

  // 드래그 중
  const handleMouseMove = useCallback((e) => {
    if (!isDragging) return;
    e.preventDefault();

    const container = scrollContainerRef.current;
    if (!container) return;

    const x = e.pageX - container.offsetLeft;
    const walk = (x - startX) * 2; // 스크롤 속도 조절
    container.scrollLeft = scrollLeft - walk;
  }, [isDragging, startX, scrollLeft]);

  // 드래그 종료
  const handleMouseUp = useCallback(() => {
    setIsDragging(false);
    const container = scrollContainerRef.current;
    if (container) {
      container.style.cursor = 'grab';
    }
  }, []);

  // 마우스가 컨테이너를 벗어날 때
  const handleMouseLeave = useCallback(() => {
    setIsDragging(false);
    const container = scrollContainerRef.current;
    if (container) {
      container.style.cursor = 'grab';
    }
  }, []);

  // 스크롤 인디케이터 업데이트
  useEffect(() => {
    const container = scrollContainerRef.current;
    if (!container || itemCount <= 1) return;

    const handleScroll = () => {
      const containerWidth = container.offsetWidth;
      const scrollWidth = container.scrollWidth;
      const currentScrollLeft = container.scrollLeft;

      // 현재 스크롤 위치를 기반으로 인디케이터 계산
      const totalScrollable = scrollWidth - containerWidth;
      if (totalScrollable <= 0) {
        setCurrentScrollIndex(0);
        return;
      }

      const scrollPercentage = currentScrollLeft / totalScrollable;
      const index = Math.round(scrollPercentage * (itemCount - 1));
      setCurrentScrollIndex(Math.max(0, Math.min(index, itemCount - 1)));
    };

    container.addEventListener('scroll', handleScroll);
    return () => container.removeEventListener('scroll', handleScroll);
  }, [itemCount]);

  // 컨테이너에 적용할 props들을 반환
  const dragScrollProps = {
    ref: scrollContainerRef,
    className: "cursor-grab select-none",
    style: {
      scrollbarWidth: 'none', // Firefox
      msOverflowStyle: 'none', // IE/Edge
    },
    onMouseDown: handleMouseDown,
    onMouseMove: handleMouseMove,
    onMouseUp: handleMouseUp,
    onMouseLeave: handleMouseLeave,
  };

  return {
    dragScrollProps,
    currentScrollIndex,
    isDragging,
  };
};