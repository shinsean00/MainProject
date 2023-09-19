$(document).ready(function(){
    let currentPage = 1;
    let itemsPerPage = parseInt(document.getElementById('itemsPerPageSelect').value, 10);
    const totalItems = parseInt(document.getElementById('usersLength').textContent, 10);
    const pageType = document.body.getAttribute('data-page-type');
    let userNum;
    
    document.getElementById('previousPageButton').addEventListener('click', previousPage);
    document.getElementById('nextPageButton').addEventListener('click', nextPage);

    function formatDate(dateString) {
        const date = new Date(dateString);
        const year = date.getFullYear();
        const month = ('0' + (date.getMonth() + 1)).slice(-2);
        const day = ('0' + date.getDate()).slice(-2);
        return year + '-' + month + '-' + day;
    }

    // 탈퇴일로부터 1년이 지난 사용자 행 강조 함수
    function highlightOldWithdrawnUsers() {
        $('tbody .user-row').each(function() {
            const withdrawalDateCell = $(this).find('.withdrawal-date');
            const withdrawalDateStr = withdrawalDateCell.text();

            const withdrawalDate = new Date(withdrawalDateStr);
            const currentDate = new Date();
            const differenceInTime = currentDate - withdrawalDate;
            const differenceInDays = differenceInTime / (1000 * 3600 * 24);

            if (differenceInDays >= 365) {
                $(this).addClass('old-withdrawn-user');
            }
        });
    }

    // 테이블 데이터 표시 함수
    function displayTableData() {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const currentItems = users.slice(start, end);
        
        document.getElementById('startRange').textContent = start + 1;
        document.getElementById('endRange').textContent = Math.min(end, totalItems);

        let tbodyContent = '';
        currentItems.forEach(user => {
            if (pageType === "userManagement") {
                tbodyContent += `
                    <tr class="user-row">
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td>${user.rank}</td>
                        <td>${formatDate(user.birth)}</td>
                        <td>${user.gender === 'M' ? '남성' : '여성'}</td>
                        <td>${user.is_deleted}</td>
                    </tr>
                `;
            } else if (pageType === "userRankManagement") {
                const switchText = user.is_admin == 'USER' ? '관리자 전환' : '사용자 전환';
                tbodyContent += `
                    <tr class="user-row">
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td>${user.rank}</td>
                        <td>${formatDate(user.birth)}</td>
                        <td><button class="table_btn rank_up_btn">등급 승격</button></td>
                        <td><button class="table_btn rank_switch_btn">${switchText}</button></td>
                    </tr>
                `; 
            } else if (pageType === "newUserManagement") {
                tbodyContent += `
                    <tr class="user-row">
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td>${formatDate(user.birth)}</td>
                        <td>${user.gender === 'M' ? '남성' : '여성'}</td>
                        <td><strong>${formatDate(user.created_at)}</strong></td>
                    </tr>
                `; 
            } else if (pageType === "withdrawnUserManagement") {
                tbodyContent += `
                    <tr class="user-row">
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td class="withdrawal-date">${formatDate(user.deleted_at)}</td>
                        <td>${user.deleted_reason}</td>
                        <td><button class="table_btn withdrawn_delete_btn">계정 삭제</button></td>
                    </tr>
                `; 
            } else if (pageType === "suspendedUserManagement") {
                tbodyContent += `
                    <tr class="user-row">
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${formatDate(user.suspended_at)}</td>
                        <td>${user.suspension_duration === 10000 ? '영구 정지' : `${user.suspension_duration}일`}</td>
                        <td>${user.suspended_reason}</td>
                        <td>${user.suspend_user_num}</td>
                        <td><button class="table_btn suspend_btn">정지 해제</td>
                    </tr>
                `; 
            }
        });
        document.querySelector('tbody').innerHTML = tbodyContent;
        highlightOldWithdrawnUsers();
    }

    // 페이지 업데이트 함수
    function updatePageDisplay() {
        const totalPages = Math.ceil(totalItems / itemsPerPage);
    
        let startPage = Math.max(1, currentPage - 2);
        let endPage = Math.min(totalPages, currentPage + 2);
    
        if (totalPages <= 5) {
            startPage = 1;
            endPage = totalPages;
        }
    
        const pageNumberContainer = document.getElementById('pageNumberContainer');
        let pageLinks = '';
    
        for (let i = startPage; i <= endPage; i++) {
            if (i === currentPage) {
                pageLinks += `<span class="current-page" style="font-weight: bold; color: #14a6b8;">${i}</span>`;
            } else {
                pageLinks += `<span class="page-link" data-page="${i}" style="cursor: pointer;">${i}</span>`;
            }
        }
    
        pageNumberContainer.innerHTML = pageLinks;
    
        // 페이지 번호 클릭 이벤트 추가
        document.querySelectorAll('.page-link').forEach(link => {
            link.addEventListener('click', function() {
                currentPage = parseInt(this.getAttribute('data-page'), 10);
                displayTableData();
                updatePageDisplay();
            });
        });
    }

    // 이벤트 핸들러 함수 선언
    function previousPage() {
        if (currentPage > 1) {
            currentPage--;
            displayTableData();
            updatePageDisplay();
        }
    }
    function nextPage() {
        if (currentPage < Math.ceil(totalItems / itemsPerPage)) {
            currentPage++;
            displayTableData();
            updatePageDisplay();
        }
    }

    // 이벤트 리스너 연결
    document.querySelector('.pagination > button:nth-child(1)').addEventListener('click', previousPage);
    document.querySelector('.pagination > button:nth-child(3)').addEventListener('click', nextPage);
    document.getElementById('itemsPerPageSelect').addEventListener('change', function() {
        itemsPerPage = parseInt(this.value, 10);
        currentPage = 1;
        displayTableData();
        updatePageDisplay();
    });

    // Sort 기능 로직
    $('body').on('click', '.sort_btn', function(){ // 처음 클릭 시 오름차 순 정렬
        $('.sort_btn, .sort_btn_up, .sort_btn_down').not(this).removeClass('sort_btn_up').removeClass('sort_btn_down').addClass('sort_btn');
        $(this).removeClass('sort_btn').addClass('sort_btn_up');
        // 클릭된 열의 인덱스를 찾습니다.
        var columnIndex = $(this).parent().index();
        var table = $(this).closest('table');
        
        // 해당 열의 데이터를 기준으로 행을 정렬
        var rows = table.find('tbody tr').sort(function(a, b) {
            var cellA = $(a).find('td').eq(columnIndex).text();
            var cellB = $(b).find('td').eq(columnIndex).text();
            
            // 데이터가 숫자인지 확인
            if (!isNaN(cellA) && !isNaN(cellB)) {
                return parseFloat(cellB) - parseFloat(cellA);  // 숫자로 변환하여 내림차순 정렬
            } else {
                return cellB.localeCompare(cellA);  // 문자열을 내림차순으로 정렬
            }
        });
        
        // 정렬된 행을 다시 tbody에 삽입
        table.find('tbody').empty().append(rows);
    });

    // 오름차순 버튼 클릭시 내림차순 버튼으로 변경 열 내림차로 정렬 로직
    $('body').on('click', '.sort_btn_up', function() { 
        $(this).removeClass('sort_btn_up').addClass('sort_btn_down');
        // 클릭된 열의 인덱스를 찾음
        var columnIndex = $(this).parent().index();
        var table = $(this).closest('table');
        
        // 해당 열의 데이터를 기준으로 행을 정렬
        var rows = table.find('tbody tr').sort(function(a, b) {
            var cellA = $(a).find('td').eq(columnIndex).text();
            var cellB = $(b).find('td').eq(columnIndex).text();
            
            // 데이터가 숫자인지 확인
            if (!isNaN(cellA) && !isNaN(cellB)) {
                return parseFloat(cellA) - parseFloat(cellB);  // 숫자로 변환하여 오름차순 정렬
            } else {
                return cellA.localeCompare(cellB);  // 문자열을 오름차순으로 정렬
            }
        });
        
        // 정렬된 행을 다시 tbody에 삽입
        table.find('tbody').empty().append(rows);
    });

    // 내림차순 버튼 클릭 시 오름차순 버튼으로 변경 후 열 오름차로 정렬 로직
    $('body').on('click', '.sort_btn_down', function() { 
        $(this).removeClass('sort_btn_down').addClass('sort_btn_up');
        // 클릭된 열의 인덱스를 찾음
        var columnIndex = $(this).parent().index();
        var table = $(this).closest('table');
        
        // 해당 열의 데이터를 기준으로 행을 정렬
        var rows = table.find('tbody tr').sort(function(a, b) {
            var cellA = $(a).find('td').eq(columnIndex).text();
            var cellB = $(b).find('td').eq(columnIndex).text();
            
            // 데이터가 숫자인지 확인
            if (!isNaN(cellA) && !isNaN(cellB)) {
                return parseFloat(cellB) - parseFloat(cellA);  // 숫자로 변환하여 내림차순 정렬
            } else {
                return cellB.localeCompare(cellA);  // 문자열을 내림차순으로 정렬
            }
        });
        
        // 정렬된 행을 다시 tbody에 삽입
        table.find('tbody').empty().append(rows);
    });

    // 검색 기능 로직
    document.querySelector('.table-search-area > input').addEventListener('input', function() {
        const searchQuery = this.value.toLowerCase();
        let filteredUsers = [];

        // 사용자 목록을 필터링
        if (pageType === "userManagement") {
            filteredUsers = window.users.filter(user => 
                user.user_num.toString().toLowerCase().includes(searchQuery) ||
                user.id.toLowerCase().includes(searchQuery) ||
                user.name.toLowerCase().includes(searchQuery) ||
                user.nickname.toLowerCase().includes(searchQuery) ||
                user.rank.toString().toLowerCase().includes(searchQuery) ||
                formatDate(user.birth).toLowerCase().includes(searchQuery) ||
                (user.gender === 'M' ? '남성' : '여성').toLowerCase().includes(searchQuery) ||
                user.is_deleted.toString().toLowerCase().includes(searchQuery)
            );
        } else if (pageType === "userRankManagement") {
            filteredUsers = window.users.filter(user => 
                user.user_num.toString().toLowerCase().includes(searchQuery) ||
                user.id.toLowerCase().includes(searchQuery) ||
                user.name.toLowerCase().includes(searchQuery) ||
                user.nickname.toLowerCase().includes(searchQuery) ||
                user.rank.toString().toLowerCase().includes(searchQuery) ||
                formatDate(user.birth).toLowerCase().includes(searchQuery)
            );
        } else if (pageType === "newUserManagement") {
            filteredUsers = window.users.filter(user => 
                user.user_num.toString().toLowerCase().includes(searchQuery) ||
                user.id.toLowerCase().includes(searchQuery) ||
                user.name.toLowerCase().includes(searchQuery) ||
                user.nickname.toLowerCase().includes(searchQuery) ||
                formatDate(user.birth).toLowerCase().includes(searchQuery) ||
                (user.gender === 'M' ? '남성' : '여성').toLowerCase().includes(searchQuery) ||
                user.created_at.toString().toLowerCase().includes(searchQuery)
            );
        } else if (pageType === "withdrawnUserManagement") {
            filteredUsers = window.users.filter(user => 
                user.user_num.toString().toLowerCase().includes(searchQuery) ||
                user.id.toLowerCase().includes(searchQuery) ||
                user.name.toLowerCase().includes(searchQuery) ||
                user.nickname.toLowerCase().includes(searchQuery) ||
                formatDate(user.deleted_at).toLowerCase().includes(searchQuery) ||
                user.deleted_reason.toLowerCase().includes(searchQuery)
            );
        } else if (pageType === "suspnededUserManagement") {
            filteredUsers = window.users.filter(user => 
                user.user_num.toString().toLowerCase().includes(searchQuery) ||
                user.id.toLowerCase().includes(searchQuery) ||
                user.name.toLowerCase().includes(searchQuery) ||
                formatDate(user.suspended_at).toLowerCase().includes(searchQuery) ||
                user.suspension_duration.toLowerCase().includes(searchQuery) ||
                user.suspended_reason.toLowerCase().includes(searchQuery) ||
                user.suspend_user_num.toLowerCase().includes(searchQuery)
            );
        }
        // 필터링된 사용자 목록을 기반으로 테이블 데이터를 표시
        displayFilteredTableData(filteredUsers);
    });

    function displayFilteredTableData(filteredUsers) {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const currentItems = filteredUsers.slice(start, end);
        
        document.getElementById('startRange').textContent = start + 1;
        document.getElementById('endRange').textContent = Math.min(end, filteredUsers.length);

        let tbodyContent = '';
        currentItems.forEach(user => {
            if (pageType === "userManagement") {
                tbodyContent += `
                    <tr>
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td>${user.rank}</td>
                        <td>${formatDate(user.birth)}</td>
                        <td>${user.gender === 'M' ? '남성' : '여성'}</td>
                        <td>${user.is_deleted}</td>
                    </tr>
                `;
            } else if (pageType === "userRankManagement") {
                const switchText = user.is_admin == 'USER' ? '관리자 전환' : '사용자 전환';
                tbodyContent += `
                    <tr>
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td>${user.rank}</td>
                        <td>${formatDate(user.birth)}</td>
                        <td><button class="table_btn rank_up_btn">등급 승격</button></td>
                        <td><button class="table_btn rank_switch_btn">${switchText}</button></td>
                    </tr>
                `;
            } else if (pageType === "newUserManagement") {
                tbodyContent += `
                    <tr>
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td>${formatDate(user.birth)}</td>
                        <td>${user.gender === 'M' ? '남성' : '여성'}</td>
                        <td><strong>${formatDate(user.created_at)}</strong></td>
                    </tr>
                `;
            } else if (pageType === "withdrawnUserManagement") {
                tbodyContent += `
                    <tr class="user-row">
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${user.nickname}</td>
                        <td class="withdrawal-date">${formatDate(user.deleted_at)}</td>
                        <td>${user.deleted_reason}</td>
                        <td><button class="table_btn withdrawn_delete_btn">계정 삭제</button></td>
                    </tr>
                `; 
            } else if (pageType === "suspendedUserManagement") {
                tbodyContent += `
                    <tr class="user-row">
                        <td>${user.user_num}</td>
                        <td>${user.id}</td>
                        <td>${user.name}</td>
                        <td>${formatDate(user.suspended_at)}</td>
                        <td>${user.suspension_duration === 10000 ? '영구 정지' : `${user.suspension_duration}일`}</td>
                        <td>${user.suspended_reason}</td>
                        <td>${user.suspend_user_num}</td>
                        <td><button class="table_btn suspend_btn" th:if="${!user.is_deleted}" th:text="${user.is_suspended ? '정지 해제' : '정지'}"></td>
                    </tr>
                `; 
            }
        });
        document.querySelector('tbody').innerHTML = tbodyContent;
        highlightOldWithdrawnUsers();
        updatePageDisplayForFilteredUsers(filteredUsers.length);
    }

    function updatePageDisplayForFilteredUsers(filteredLength) {
        document.getElementById('currentPageDisplay').textContent = currentPage;
        document.getElementById('totalPagesDisplay').textContent = Math.ceil(filteredLength / itemsPerPage);
    }

    // 초기 표시
    displayTableData();
    updatePageDisplay();

    
    // 행 클릭 이벤트 리스너
    $('body').off('click', 'tbody tr.user-row').on('click', 'tbody tr.user-row', function() {
    const clickedRow = $(this);
    let userNum = clickedRow.find('td:first').text().trim();
    
    // 상세 내용이 이미 표시되어 있다면 제거
    if (clickedRow.next().hasClass('detail-content-row')) {
        clickedRow.next().remove();
        return;
    }

    // 다른 행의 상세 내용 제거
    $('.detail-content-row').remove();
    
    // 그렇지 않다면 상세 내용 로드
    let colspanValue = 8;
    if (pageType === "newUserManagement" || pageType === "withdrawnUserManagement") {
        colspanValue = 7; 
    } 

    $('<td colspan="' + colspanValue + '"></td>').load('/privacy-admin/user-management/user-detail.do?user_num=' + userNum, function(response, status, xhr) {
            if (status == "error") {
                console.error("Error loading detail content:", xhr.status + " " + xhr.statusText);
                return;
            }
            
            const detailContent = `
                <tr class="detail-content-row">
                    <td colspan="${colspanValue}" class="detail-container">
                        ${response}
                    </td>
                </tr>
            `;
        
            // 가져온 내용을 현재 클릭된 행 바로 다음에 삽입
            clickedRow.after(detailContent);

            // 수정 버튼
            $('#update_btn').on('click', function() {
                if (userNum) { // userNum 값이 설정되어 있는지 확인
                    $('.detail-container').load('/privacy-admin/user-management/user-detail-update.do?user_num=' + userNum);
                } else {
                    console.error('userNum is not defined!');
                }
            });
            
            // 닉네임 변수
            const nickname = $('#detail-user-nickname').text();

            // 모달 변수
            const modal = document.getElementById("suspendModal");

            // 정지 버튼 클릭 이벤트
            $('#suspend_btn').on('click', function() {
                const confirmationMessage = nickname + "님의 계정을 정지시키시겠습니까?";
                if (!window.confirm(confirmationMessage)) {
                    return;
                }
                modal.style.display = "block";
            });

            // 정지 모달 내의 "확인" 버튼 클릭 이벤트
            const confirmBtn = document.getElementById("suspend_confirm_btn");
            confirmBtn.onclick = function() {
                const reason = document.getElementById("suspended_reason").value;
                const duration = document.getElementById("suspension_duration").value;
                const errorMessage = document.querySelector(".suspend-error");
                
                // 유효성 검사
                if (!reason.trim()) {
                    errorMessage.textContent = "정지 사유를 입력해주세요.";
                    return false;
                }
                if (!duration.trim()) {
                    errorMessage.textContent = "정지 기한을 선택해주세요.";
                    return false;
                }
                errorMessage.textContent = "";

                // ajax 호출을 실행하여 사용자를 정지
                const userNum = $('#userData').text();
                if (userNum) {
                    $.ajax({
                        url: "/api/suspend-user",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify({
                            userNum: userNum,
                            action: 'suspend',
                            reason: reason,
                            duration: duration
                        }),
                        dataType: 'json',
                        success: function(response) {
                            if (response.status === 'success') {
                                alert(nickname + "님의 계정이 정지되었습니다.");
                                location.reload();
                            } else {
                                console.error('Error suspending user:', response.message);
                            }
                        },
                        error: function(xhr, status, error) {
                            alert("회원 정지 중 오류가 발생했습니다. 상태: " + status + ", 응답: " + xhr.responseText);
                        }
                    });
                } else {
                    console.error('userNum is not defined!');
                }
                console.log(reason);
                modal.style.display = "none";
            };

            // 모달 닫기 버튼
            $('.close-btn').on('click', function() {
                $('#suspendModal').hide();
            });

            // 모달 외부 클릭 시 모달 닫기
            $(window).on('click', function(event) {
                if ($(event.target).is('#suspendModal')) {
                    $('#suspendModal').hide();
                }
            });


            // 정지 해제 버튼 클릭 이벤트
            $('#unsuspend_btn').on('click', function() {
                const nickname = $('#detail-user-nickname').text();
                const confirmationMessage = nickname + "님의 정지를 정말 해제하시겠습니까?";
                if (!window.confirm(confirmationMessage)) {
                    return;
                }

                const userNum = $('#userData').text();
                if (userNum) {
                    $.ajax({
                        url: "/api/suspend-user",
                        type: "POST",
                        contentType: "application/json",
                        data: JSON.stringify({
                            userNum: userNum,
                            action: 'unsuspend'
                        }),
                        dataType: 'json',
                        success: function(response) {
                            if (response.status === 'success') {
                                alert(nickname + "님의 계정이 정지 해제되었습니다.");
                                location.reload();
                            } else {
                                console.error('Error unsuspending user:', response.message);
                            }
                        },
                        error: function(xhr, status, error) {
                            alert("회원 정지 해제 중 오류가 발생했습니다. 상태: " + status + ", 응답: " + xhr.responseText);
                        }
                    });
                } else {
                    console.error('userNum is not defined!');
                }
            });

            // 삭제 버튼
            $('#delete_btn').on('click', function() {
                const nickname = $('#detail-user-nickname').text();
                // 삭제 확인 알림
                let confirmationMessage = nickname + "님의 계정을 정말 삭제시키시겠습니까?";
 
                if (!window.confirm(confirmationMessage)) {
                    return;
                }

                // ajax 회원 정보 삭제 post 요청
                $.ajax({
                    url: "/api/remove-user",
                    type: "POST",
                    contentType: "application/json",
                    data: JSON.stringify({ userNum: userNum }),
                    success: function(response) {
                        alert(nickname + "님의 계정이 삭제되었습니다.");
                        location.reload();
                    },
                    error: function(xhr, status, error) {
                        alert("회원 삭제 중 오류가 발생했습니다. 상태: " + status + ", 응답: " + xhr.responseText);
                    }
                }); 
            });
        });
    });
});
