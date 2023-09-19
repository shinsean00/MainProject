// 나의 반려친구 나이 계산 함수
function calculateAge(birthDateString) {
    const today = new Date();
    const birthDate = new Date(birthDateString);
    
    let age = today.getFullYear() - birthDate.getFullYear();
    const monthDifference = today.getMonth() - birthDate.getMonth();
    
    // 생일이 아직 오지 않은 경우 나이를 조정
    if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }
    
    return age;
}

document.addEventListener('DOMContentLoaded', function() {
    // 모달 버튼 클릭시 모달 표시
    const modalButtons = document.querySelectorAll('.modal-btn');
    modalButtons.forEach(button => {
        button.addEventListener('click', function() {
            // 해당 버튼에 연결된 모달만
            const modalId = button.getAttribute('data-modal-id');
            if (modalId) {
                const modal = document.getElementById(modalId);
                if (modal) {
                    modal.style.display = "block";
                }
            }
        });
    });

    // 모달 닫기 버튼
    const closeButtons = document.querySelectorAll('.close-btn');
    closeButtons.forEach(button => {
        button.addEventListener('click', function() {
            const modal = button.closest('.modal');
            if (modal) {
                modal.style.display = "none";
            }
        });
    });

    // 모달 외부 클릭 시 모달 닫기
    window.addEventListener('click', function(event) {
        if (event.target.classList.contains('modal')) {
            event.target.style.display = "none";
        }
    });

    var calendarEl = document.getElementById('calendar');
    var today = new Date().toISOString().split('T')[0];

    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialDate: today, // 캘린더가 처음 표시될 때의 날짜
        locale: 'es',
        editable: false, // 이벤트 드래그 여부
        selectable: true, // 날짜 시간 범위 선택 여부
        businessHours: true, // 업무시간 표시 여부
        dayMaxEvents: true, // 하루 표시할 이벤트 수 제한 
        eventDidMount: function(info) { // 툴팁 
            var tooltip = new Tooltip(info.el, {
              title: info.event.extendedProps.description,
              placement: 'top',
              trigger: 'hover',
              container: 'body'
            });
        },
        events: eventsFromServer.map(event => {
            let eventObject = {
                id: event.event_num,
                title: event.title,
                start: event.started_at,
                end: event.ended_at,
                allday: event.allday,
                description: event.content,
            };
        
            if (event.url) {
                eventObject.url = event.url;
            }
        
            return eventObject;
        }),
    });
    calendar.setOption('locale', 'ko');
    calendar.render();

    
    // 나이 출력
    const birthSpan = document.getElementById('pet-birth');
    if (birthSpan) {
        const ageSpan = birthSpan.parentElement.previousElementSibling.querySelector('span');
        const age = calculateAge(birthSpan.textContent);
        ageSpan.textContent = age + "세";
    }
});
