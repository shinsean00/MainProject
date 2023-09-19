document.addEventListener("DOMContentLoaded", function() {
    var loginBtn = document.getElementById('loginLink_btn');
    var joinBtn = document.getElementById('joinLink_btn');
    var logoutBtn = document.getElementById('logoutLink_btn');
    var mypageBtn = document.getElementById('mypageLink_btn');

    if (loginBtn) {
        loginBtn.addEventListener('click', function() {
            window.location.href = '/user/login.do';
        });
    }
    if (joinBtn) {
        joinBtn.addEventListener('click', function() {
            window.location.href = '/user/join.do';
        });
    }
    if (logoutBtn) {
        logoutBtn.addEventListener('click', function() {
            window.location.href = '/api/logout';
        });
    }
    if (mypageBtn) {
        mypageBtn.addEventListener('click', function() {
            window.location.href = '/mypage/main.do';
        });
    }
});