function calculateAge(birth) {
	const birthDate = new Date(birth);
	const today = new Date();
	// 나이 기본값을 1로 시작하게 변경
	let age = today.getFullYear() - birthDate.getFullYear() ;
	const monthDifference = today.getMonth() - birthDate.getMonth();

	if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
		age--;
	}

	return age;
}

// DOM이 완전히 로드된 후에 나이 계산 함수 실행
document.addEventListener('DOMContentLoaded', function() {
	const ageCells = document.getElementsByClassName('age');

	for (let ageCell of ageCells) {
		const birth = ageCell.dataset.birth;
		const age = calculateAge(birth)+ 1;
		ageCell.innerText = age + "살";
	}

	// $("#pet-register-btn").click(function() {
	// 	window.location.href = "/mypage/pet-join.do";
	// });
});

