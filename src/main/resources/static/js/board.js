function redirectToArticlePage(row) {
    var postNum = $(row).find('.postNum').text();
    window.location.href = "/board/viewArticle.do?post_num=" + postNum;
}

function redirectToArticleForm() {
    window.location.href = "/board/articleForm.do";
}

$(document).ready(function() {
});
