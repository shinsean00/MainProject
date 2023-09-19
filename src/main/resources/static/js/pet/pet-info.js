var originalProfile;
var croppedImageBlob = null;

$(document).ready(function() {
	var originalProfile = $("#curProfileImage").attr("src");

	// 프로필 사진 변경 시 메시지 리스너 추가
	window.addEventListener("message", function(event) {
		let croppedImageBase64 = event.data.base64;
		croppedImageBlob = event.data.blob;
		if (croppedImageBase64) {
			changeEditProfile(croppedImageBase64);
		}
		// 프로필 사진 변경 버튼 비활성화
		$("#editProfile").prop("disabled", true);
	}, false);
	function changeEditProfile(base64) {
		$("#curProfileImage").attr("src", base64);
		$("#profileInput").val(base64);
	}

	// 프로필 사진 변경 버튼
	$("#editProfile").click(function() {
		window.open("/mypage/my-info-profile-edit.do?currentProfilePicSrc=" + originalProfile, '_blank', 'width=1030,height=630,scrollbars=no,menubar=no,toolbar=no,location=no');
	});

	// 프로필 초기화 버튼
	$("#resetProfile").click(function() {
		$("#curProfileImage").attr("src", originalProfile);
		$("#profileInput").val(originalProfile);
	});
});

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
	agePara.innerText = age + '살';
});

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

function submitUpdates() {
	const petNo = document.getElementById("petNo").value;
	const name = document.getElementById("nameUpdateInput").value;
	const birth = document.getElementById("birthUpdateInput").value;
	const adopted_at = document.getElementById("adoptedAtUpdateInput").value;
	const updated_at = getCurrentTimestamp(); // 현재 시각 추가
	const currentTime = new Date();

	if (birth > currentTime || birth > adopted_at) {
		alert('생일은 현재시각이나 입양일보다 이전이어야 합니다.');
		return;
	}

	if (adopted_at > currentTime) {
		alert('입양일은 현재시각보다 이전이어야 합니다.');
		return;
	}
	
	var profileInput = $("#profileInput").val();
    if (profileInput !== originalProfile && croppedImageBlob) {
        saveEditProfile(croppedImageBlob);
    }

	// 프로필 사진 변경시 프로필 업로드
	function saveEditProfile(blob) {
		var profileData = new FormData();
		if (!(blob instanceof Blob)) {
        return;
    }
		profileData.append("profile_picture", blob, petNo + ".png");

		$.ajax({
			url: "/api/update-pet-profile",
			type: "POST",
			data: profileData,
			processData: false,
			contentType: false,
			success: function(response) {
				alert("프로필 사진이 업데이트되었습니다.");
			},
			error: function() {
				alert("프로필 사진 업데이트 중 오류가 발생했습니다.");
			}
		});
	}
	var profileInput = $("#profileInput").val();
	if (profileInput !== originalProfile) {
		saveEditProfile(croppedImageBlob);
	}


	// AJAX를 사용해 서버에 수정된 정보를 전송합니다.
	$.ajax({
		url: '/api/update-pet',
		contentType: 'application/json',
		data: JSON.stringify({ pet_no: petNo, name, birth, adopted_at, updated_at }),
		type: 'POST',
		dataType: 'json',  // Expect a JSON response
		success: function(response) {
			if (response.status === 'success') {
				alert('반려친구 정보 수정되었습니다.');
				location.href = response.redirectUrl;
			} else {
				alert('반려친구 정보 수정을 실패하였습니다.');
			}
		},
		error: function() {
			alert('반려친구 정보 수정을 실패하였습니다.');
		}
	});
}

function submitDelete() {
	const petNo = document.getElementById("petNo").value;
	const deleted_at = getCurrentTimestamp();

	$.ajax({
		url: '/api/delete-pet',
		contentType: 'application/json',
		data: JSON.stringify({ pet_no: petNo, is_deleted: true, deleted_at }),
		type: 'POST',
		dataType: 'json', // Expect a JSON response
		success: function(response) {
			if (response.status === 'success') {
				alert('반려친구 삭제되었습니다.');
				location.href = response.redirectUrl;
			} else {
				alert('반려친구 삭제에 실패하였습니다.');
			}
		},
		error: function() {
			alert('반려친구 삭제에 실패하였습니다.')
		}
	});
}

