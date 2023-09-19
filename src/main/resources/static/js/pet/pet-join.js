$(document).ready(function() {

    $('#pet-modal-register-btn').on('click', function(event) {
        let profile_picture;
        const nameInput = $('#nameInput').val();
        const birth_input = new Date($('#birth_input').val());
        const adopted_at_input = new Date($('#adopted_at_input').val());
        const currentTime = new Date();

        function showError(message) {
            $('#registerError').text(message);
        }

        if (birth_input > currentTime || birth_input > adopted_at_input) {
            showError('생일은 현재시각이나 입양일보다 이전이어야 합니다.');
            return;
        }

        if (adopted_at_input > currentTime) {
            showError('입양일은 현재시각보다 이전이어야 합니다.');
            return;
        }
        
        if (!nameInput) {
            showError('반려친구 이름을 입력해 주세요.');
            return;
        }

        if ($('#birth_input').val() === '' || isNaN(birth_input.getTime())) {
            showError('유효한 생일을 입력해 주세요.');
            return;
        }

        if ($('#adopted_at_input').val() === '' || isNaN(adopted_at_input.getTime())) {
            showError('유효한 입양일을 입력해 주세요.');
            return;
        }

        event.preventDefault();

        const species = $('input[name=species]:checked').val();

        if (species == "animal") {
            profile_picture = "/img/pet-profile/pet.png"
        } else if (species == "plant") {
            profile_picture = "/img/pet-profile/plant.png"
        } else {
            showError("반려친구의 종을 선택해주세요.");
            return;
        }
        
        const petData = {
            name: $('#nameInput').val(),
            birth: $('#birth_input').val(),
            adopted_at: $('#adopted_at_input').val(),
            profile_picture: profile_picture
        };

        $.ajax({
            url: '/api/register-pet',
            contentType: 'application/json',
            data: JSON.stringify(petData),
            type: 'POST',
            dataType: 'json',  // Expect a JSON response
            success: function(response) {
                if (response.status === 'success') {
                    alert('새로운 반려친구를 등록하였습니다.');
                    location.href = response.redirectUrl;
                } else {
                    showError('반려친구 등록을 실패하였습니다.');
                }
            },
            error: function() {
                showError('반려친구 등록을 실패하였습니다.');
            }
        });
    });
});
