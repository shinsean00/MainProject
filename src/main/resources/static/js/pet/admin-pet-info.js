function calculateAge(birth) {
	const birthDate = new Date(birth);
	const today = new Date();
	let age = today.getFullYear() - birthDate.getFullYear() + 1;
	const monthDifference = today.getMonth() - birthDate.getMonth();

	if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
		age--;
	}

	return age;
}

// DOM이 완전히 로드된 후에 나이 계산 함수 실행
document.addEventListener('DOMContentLoaded', function() {
	const agePara = document.querySelector('.age');
	const birth = agePara.dataset.birth;
	const age = calculateAge(birth);
	agePara.innerText =   age + '살';
});

//수정폼을 보여주는 기능
function showEditForm() {
	document.getElementById('info-display').style.display = 'none';
	document.getElementById('edit-form').style.display = 'block';
}

function hideEditForm() {
	document.getElementById('edit-form').style.display = 'none';
	document.getElementById('info-display').style.display = 'block';

}
function getCurrentTimestamp() {
	let date = new Date();
	let offset = 9 * 60 * 60 * 1000;
	let kstDate = new Date(date.getTime() + offset);

	return kstDate.toISOString();
}

function selectUserNum(userNum) {
	document.getElementById('adoptedUserNumUpdateInput').value = userNum;
}

function selectUserId(userId) {
	document.getElementById('adoptedUserIdUpdateInput').value = userId;
}
// 아래는 페이지가 로드되었을 때 실행되는 코드입니다.
window.onload = function() {
	var rows = document.getElementsByTagName("tr"); // 모든 행 가져오기

	for (var i = 0; i < rows.length; i++) { // 각각의 행에 대하여
		rows[i].onclick = function() { // 클릭 이벤트 추가
			var userNumCell = this.cells[0]; // 첫 번째 셀은 회원번호 셀입니다.
			var userIdCell = this.cells[1];
			selectUserNum(userNumCell.textContent); // 회원번호를 함수에 전달합니다.
			selectUserId(userIdCell.textContent);
		};
	}
};




// 검색 기능
function searchFunction() {
	// 검색어 가져오기
	var input, filter, table, tr, td, i;
	input = document.getElementById("searchInput");
	filter = input.value.toUpperCase();

	// 카테고리 가져오기 (회원번호:0, 아이디:1, 반려인:2)
	var category = document.getElementById("searchCategory").value;


	// 테이블과 행 가져오기
	table = document.getElementById("userTable");
	tr = table.getElementsByTagName("tr");

	// 각 행에 대해 반복하여 내용 비교
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[category];
		if (td) {
			if (td.innerHTML.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = ""; // 일치하는 경우 보여주기
			} else {
				tr[i].style.display = "none"; // 일치하지 않는 경우 숨기기
			}
		}
	}
}


function submitUpdates() {
	const petNo = document.getElementById("petNo").value;
	const name = document.getElementById("nameUpdateInput").value;
	const birth = document.getElementById("birthUpdateInput").value;
	const adopted_at = document.getElementById("adoptedAtUpdateInput").value;
	const updated_at = getCurrentTimestamp(); // 현재 시각 추가
	const adopted_user_num = document.getElementById("adoptedUserNumUpdateInput").value;
	const currentTime = new Date();

	if (birth > currentTime || birth > adopted_at) {
		alert('생일은 현재시각이나 입양일보다 이전이어야 합니다.');
		return;
	}

	if (adopted_at > currentTime) {
		alert('입양일은 현재시각보다 이전이어야 합니다.');
		return;
	}

	// AJAX를 사용해 서버에 수정된 정보를 전송합니다.
	$.ajax({
		url: '/api/update-admin-pet',
		contentType: 'application/json',
		data: JSON.stringify({ pet_no: petNo, name, birth, adopted_at, updated_at, adopted_user_num }),
		type: 'POST',
		dataType: 'json',  // Expect a JSON response
		success: function(response) {
			if (response.status === 'success') {
				alert('수정이 완료되었습니다.');
				location.href = response.redirectUrl;
			} else {
				alert('수정에 실패하였습니다.');
			}
		},
		error: function() {
			alert('수정에 실패하였습니다.');
		}
	});
}

function submitDelete() {
	const petNo = document.getElementById("petNo").value;
	const deleted_at = getCurrentTimestamp();

	$.ajax({
		url: '/api/delete-admin-pet',
		contentType: 'application/json',
		data: JSON.stringify({ pet_no: petNo, is_deleted: true, deleted_at }),
		type: 'POST',
		dataType: 'json', // Expect a JSON response
		success: function(response) {
			if (response.status === 'success') {
				alert('삭제가 완료되었습니다.');
				location.href = response.redirectUrl;
			} else {
				alert('삭제에 실패하였습니다.');
			}
		},
		error: function() {
			alert('삭제에 실패하였습니다.')
		}
	});
}


