$(document).ready(function(){
    let cropper;
    const image = document.getElementById('editCropperImage');

    // cropper 생성 메소드
    function createCropper() {
        if (cropper) {
            cropper.destroy();
        }

        cropper = new Cropper(image, {
            aspectRatio: 1 / 1,
            crop(event) {
                updatePreview();
            }
        });
    }
    // 미리보기 canvas 추적 메소드
    function updatePreview() {
        const canvasOptions = {
            width: 250,
            height: 250
        };
        const canvas = cropper.getCroppedCanvas(canvasOptions);
        const previewImage = document.getElementById('previewImage');
        previewImage.src = canvas.toDataURL('image/png');
    }
    createCropper();

    // 사진 변경 
    $("#profileImageInput").change(function() {
        var reader = new FileReader();
        var fileName = $(this).val().split('\\').pop();

        reader.onload = function(e) {
            $("#editCropperImage").attr("src", e.target.result);
            createCropper();
        }
        
        reader.readAsDataURL(this.files[0]);

        if(fileName) {
            $('.label-file-input').text(fileName);
        } else {
            $('.label-file-input').html('<img th:src="@{/img/cropper/image-plus.png}" alt="Upload Icon"> 프로필 사진 선택');
        }
    });

    // 사진 회전
    $("#rotateRightBtn").click(function() {
        cropper.rotate(45);
    });
    $("#rotateLeftBtn").click(function() {
        cropper.rotate(-45);
    });


    // 드래그 모드 변경
    $("#dragModeMoveBtn").click(function() {
        cropper.setDragMode('move');
    });
    $("#dragModeCropBtn").click(function() {
        cropper.setDragMode('crop');
    });

    // 상하반전 및 좌우반전
    let flippedHorizontal = false;
    let flippedVertical = false;
    $("#flipHorizontalBtn").click(function() {
        flippedHorizontal = !flippedHorizontal;
        cropper.scaleX(flippedHorizontal ? -1 : 1);
    });
    $("#flipVerticalBtn").click(function() {
        flippedVertical = !flippedVertical;
        cropper.scaleY(flippedVertical ? -1 : 1);
    });

    // 리셋
    $("#resetBtn").click(function() {
        cropper.reset();
    });

    // 저장
    $("#saveBtn").click(function() {
        const canvas = cropper.getCroppedCanvas();
        if (canvas) {
            const base64data = canvas.toDataURL('image/png');
            canvas.toBlob(function(blob) {
                if (blob) {
                    window.opener.postMessage({
                        blob: blob,
                        base64: base64data
                    }, '*');
                    window.close();
                } else {
                    alert("이미지를 저장할 수 없습니다.");
                }
            }, 'image/png');
        } else {
            alert("이미지를 저장할 수 없습니다.");
        }
    });
});