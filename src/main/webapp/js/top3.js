const circle = document.querySelectorAll(".circle");

circle.forEach((item) => {
  const all = item.children;

  const half1 = all[0];
  const half2 = all[1];
  const half = all[2];
  const per = all[all.length - 1].children[1];

  const number = +item.getAttribute("data-val");

  // Animate percentage
  let num = 0;
  setInterval(() => {
    if (num < number) {
      ++num;
      per.textContent = num + "%";
    } else {
      clearInterval();
    }
  }, calculateTime(number));

  // Animate progress bar
  const portion = number * 3.6;
  const time = calculateTime(portion);

  let i = 0;
  setInterval(() => {
    i++;

    if (i <= portion) {
      if (i <= 180) {
        half1.style.transform = `rotate(${i}deg)`;
      }

      if (i === 180) {
        half.style.opacity = "0";
      }

      half2.style.transform = `rotate(${i}deg)`;
    } else {
      clearInterval();
    }
  }, time);
});

function calculateTime(x) {
  return 2000 / x;
}
