
const track = document.querySelector(".carousel-track");
const slides = Array.from(track.children);
const pagination = document.querySelector(".pagination");
let current = 0;
const total = slides.length;

let autoSlideTimer;

// 슬라이드 위치 업데이트
function updateSlidePosition() {
  slides.forEach((slide, i) => {
    const diff = (i - current + total) % total;

    slide.style.opacity = "0";
    slide.style.zIndex = "1";
    slide.style.transform = `translateX(0px) scale(0.7)`;

    if (diff === 0) {
      slide.style.opacity = "1";
      slide.style.zIndex = "3";
      slide.style.transform = `translateX(0px) scale(1)`;
    } else if (diff === 1 || diff === total - 1) {
      slide.style.opacity = "1";
      slide.style.zIndex = "2";
      slide.style.transform = `translateX(${diff === 1 ? 600 : -600}px) scale(0.85)`;
    }
  });

  Array.from(pagination.children).forEach((dot, i) => {
    dot.classList.toggle("active", i === current);
  });
}

// 자동 슬라이드 재귀 함수
function startAutoSlide() {
  clearTimeout(autoSlideTimer); // 기존 타이머 제거
  autoSlideTimer = setTimeout(() => {
    current = (current + 1) % total;
    updateSlidePosition();
    startAutoSlide(); // 다음 슬라이드 예약
  }, 5000);
}

// 페이지네이션 dot 생성 및 클릭 이벤트
slides.forEach((_, i) => {
  const dot = document.createElement("div");
  dot.classList.add("dot");
  if (i === current) dot.classList.add("active");

  dot.addEventListener("click", () => {
    current = i;
    updateSlidePosition();
    startAutoSlide(); // 타이머 리셋
  });

  pagination.appendChild(dot);
});

// 초기 실행
updateSlidePosition();
startAutoSlide();

// 버튼 DOM
const prevBtn = document.querySelector(".prev-btn");
const nextBtn = document.querySelector(".next-btn");

// 이전 버튼 클릭
prevBtn.addEventListener("click", () => {
  current = (current - 1 + total) % total;
  updateSlidePosition();
  startAutoSlide(); // 타이머 초기화
});

// 다음 버튼 클릭
nextBtn.addEventListener("click", () => {
  current = (current + 1) % total;
  updateSlidePosition();
  startAutoSlide(); // 타이머 초기화
});
