let slideIndex = 1;

$(document).ready(function() {
    showSlides(slideIndex);

    // 게시글 클릭 이벤트
    $('.board-item').click(function() {
        const postNum = $(this).data('postnum');
        window.location.href = '/board/viewArticle.do?post_num=' + postNum;
    });

    setInterval(function() {
        moveSlide(1);
    }, 6000);
});

function moveSlide(n) {
    showSlides(slideIndex += n);
}

function showSlides(n) {
    let i;
    const slides = document.getElementsByClassName("slide");

    if (!slides.length) {
        console.warn("No slides found!");
        return; // No slides, so exit the function
    }
    
    if (n > slides.length) {
        slideIndex = 1;
    }
    if (n < 1) {
        slideIndex = slides.length;
    }
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slides[slideIndex - 1].style.display = "block";
}
