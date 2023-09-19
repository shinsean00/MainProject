$(document).ready(function() {
    const $searchInput = $('[name="search_input"]');
    const $searchArea = $('.search-area');
    const $recentSearches = $('#recent_searches');
    const $searchBtn = $('#search_btn');

    $searchInput.on('keypress', function(e) {
        if (e.which === 13) { // 13은 엔터 키의 키 코드
            e.preventDefault(); // 기본 동작을 중지
            $searchBtn.click(); // 버튼을 클릭
        }
    });
    
    // 최근 검색어 로직
    const MAX_RECENT_SEARCHES = 10; // 최근 검색어의 최대 개수
    function saveSearchTerm(term) {
        let searches = JSON.parse(localStorage.getItem('recentSearches') || '[]');
        // 이미 같은 검색어가 있는 경우 해당 검색어를 제거
        const index = searches.indexOf(term);
        if (index !== -1) {
            searches.splice(index, 1);
        }
        // 새로운 검색어를 배열의 시작에 추가
        searches.unshift(term);
        if (searches.length > MAX_RECENT_SEARCHES) {
            searches.pop(); // 가장 오래된 검색어 제거
        }
        localStorage.setItem('recentSearches', JSON.stringify(searches));
    }
    function loadRecentSearches() {
        let searches = JSON.parse(localStorage.getItem('recentSearches') || '[]');
        $recentSearches.empty();
        if (searches.length === 0) {
            $recentSearches.append('<p class="no-recent-searches" style="font-style: italic; color: #ccc; margin: 0px;">최근 검색어가 없습니다.</p>');
        } else {
            searches.forEach(term => {
                $recentSearches.append(`<li><img class="recent-search-img" src="/img/recent-search.png"><span class="recent-search-term">${term}</span></li>`);
            });
        }
    }
    
    // 검색창 클릭 이벤트
    $searchInput.on('focus', function() {
        loadRecentSearches();
        $recentSearches.show();
        $searchArea.addClass('active');
    });
    
    // 검색창에서 포커스가 사라질 때
    $searchInput.on('blur', function() {
        setTimeout(function() {
            $recentSearches.hide();
            $searchArea.removeClass('active');
        }, 200);
    });

    // 최근 검색어 항목 클릭 로직
    $recentSearches.on('click', 'li', function() {
        console.log("List item clicked!");
        const clickedTerm = $(this).find('.recent-search-term').text();
        console.log("Clicked Term:", clickedTerm);
        $searchInput.val(clickedTerm);
        $searchBtn.click();
    });

    // 검색창에서 텍스트 입력 이벤트 - 검색 기능 직접 구현으로 폐기
    // $searchInput.on('input', function() {
    //     const query = $(this).val();
    //     if (query) {
    //         $recentSearches.hide();
    //     } else {
    //         loadRecentSearches();
    //         $recentSearches.show();
    //     }
    // });

    // 검색 로직
    $searchBtn.on('click', function() {
        const query = $searchInput.val();
        // 검색창이 비어있다면
        if (!query.trim()) {
            $searchArea.addClass('shake-animation');
            setTimeout(() => $searchArea.removeClass('shake-animation'), 500); // 0.5초 후 애니메이션 제거
            return;
        }
        saveSearchTerm(query);

        // 모달 표시
        $("#search-modal").css("display", "block");

        // AJAX 호출
        $.ajax({
            url: '/api/search-results',
            type: 'GET',
            data: {
                searchTerm: query
            },
            success: function(response) {
                let redirectUrl = `/user/search-results.do?results=${encodeURIComponent(JSON.stringify(response.results))}&searchTerm=${encodeURIComponent(response.searchTerm)}`;
                window.location.href = redirectUrl;
            },
            complete: function() {
                $("#searchModal").css("display", "none");
            }
        });
    });

    loadRecentSearches();

    // GPT 답변 한 글자씩 나타나도록 설정
    const gptAnswerElem = $('#gpt-answer');
    if (gptAnswerElem.length) {
        const text = gptAnswerElem.text();
        let index = 0;
        gptAnswerElem.text('');

        function typeText() {
            if (index < text.length) {
                gptAnswerElem.text(gptAnswerElem.text() + text[index]);
                index++;
                setTimeout(typeText, 30);
            }
        }
        typeText();
    }
});
