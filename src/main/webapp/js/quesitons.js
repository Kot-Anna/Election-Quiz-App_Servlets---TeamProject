
const form = document.getElementById("qustions-form");
const next = document.getElementById("next");
const submit = document.getElementById("submit");
const bar = document.querySelector(".bar");
const wrapper = document.querySelector(".wrapper-question");
const questionIndex = document.querySelector(".question-index");
const wrapperContent = document.querySelector(".wrapper-content");
const totalNum = document.querySelector(".total-num");
const answersArr = document.querySelector(".answersArr");
const title = document.querySelector(".title");
const label = document.querySelectorAll(".answer label");

const questions = [...wrapperContent.getAttribute("data-questions").split("%")];


let amount = calculate(questions.length);

next.addEventListener("click", update);
/*submit.addEventListener("click", redirect);*/
window.addEventListener("load", () => {
	wrapper.textContent = questions[0];
	bar.style.width = calculate(questions.length) + "%";
	totalNum.textContent = questions.length;
});

const question = questionIterator(questions);

let answers = [];

function update(e) {
	e.preventDefault();
	
	const getName = document.getElementsByName("score");
	
	for(let i = 0; i < getName.length; i++){
		if(getName[i].checked){
			answers.push(getName[i].value);
		}
	}
	
	console.log(questions.join("%"));
		answersArr.value = answers.join("%");
	

	const isDone = question.next();
	let index = +questionIndex.textContent + 1;

	if (!isDone.done) {
		questionIndex.textContent = index;
		amount += calculate(questions.length);
		bar.style.width = amount + "%";
		wrapper.textContent = isDone.value;
	}

	if (index === questions.length + 1) {
		next.style.display = "none";
		submit.style.display = "block";
		title.classList.add("disabled");
		questionIndex.classList.add("disabled");
		submit.classList.add("ready-submit");
		wrapper.textContent = "All quesions were answered!"
		wrapper.style.color = "#c4c4c4";
		
		label.forEach(i => {
			i.style.color = "#c4c4c4";
		})
		
		const getName = document.getElementsByName("score");
	
		for(let i = 0; i < getName.length; i++){
			getName[i].disabled = true;
		}
		
	}
}

/*function redirect(e) {
	e.preventDefault();
	window.location.href = "../jsp/top-3.jsp";
}*/

function questionIterator(questions) {
	let currentIndex = 1;

	return {
		next: function() {
			return currentIndex < questions.length
				? {
					value: questions[currentIndex++],
					done: false,
				}
				: { done: true };
		},
	};
}

function calculate(x){
	return 100 / x;
}
