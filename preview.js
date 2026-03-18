document.addEventListener('DOMContentLoaded', () => {
    // Navigation
    const navItems = document.querySelectorAll('.nav-item');
    const screens = document.querySelectorAll('.screen');
    const screenTitle = document.getElementById('screen-title');

    navItems.forEach(item => {
        item.addEventListener('click', () => {
            const screenName = item.getAttribute('data-screen');
            
            // Update nav active state
            navItems.forEach(nav => nav.classList.remove('active'));
            item.classList.add('active');

            // Switch screens
            screens.forEach(screen => {
                screen.classList.remove('active');
                if (screen.id === `${screenName}-screen`) {
                    screen.classList.add('active');
                }
            });

            // Update title
            screenTitle.textContent = screenName.charAt(0).toUpperCase() + screenName.slice(1);
        });
    });

    // Chart.js - Usage History
    const ctx = document.getElementById('usageChart').getContext('2d');
    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['1 PM', '2 PM', '3 PM', '4 PM', '5 PM'],
            datasets: [{
                label: 'Data Usage (MB)',
                data: [10, 25, 15, 40, 35],
                borderColor: '#6750A4',
                backgroundColor: 'rgba(103, 80, 164, 0.1)',
                fill: true,
                tension: 0.4,
                borderWidth: 2,
                pointRadius: 4,
                pointBackgroundColor: '#6750A4'
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            plugins: {
                legend: { display: false }
            },
            scales: {
                x: { grid: { display: false } },
                y: { beginAtZero: true }
            }
        }
    });

    // Simulated Actions
    const btnRestart = document.getElementById('btnRestart');
    if (btnRestart) {
        btnRestart.addEventListener('click', () => {
            btnRestart.disabled = true;
            btnRestart.textContent = 'Restarting...';
            
            setTimeout(() => {
                alert('Hotspot Restarted Successfully');
                btnRestart.disabled = false;
                btnRestart.textContent = 'Restart Hotspot';
            }, 2000);
        });
    }

    const btnBlocks = document.querySelectorAll('.btn-block');
    btnBlocks.forEach(btn => {
        btn.addEventListener('click', () => {
            const deviceName = btn.parentElement.querySelector('.device-name').textContent;
            if (confirm(`Are you sure you want to block ${deviceName}?`)) {
                btn.textContent = 'Blocked';
                btn.disabled = true;
                btn.style.borderColor = '#ccc';
                btn.style.color = '#ccc';
                btn.parentElement.querySelector('.speed-info').style.opacity = '0.3';
            }
        });
    });

    // Modal Logic
    const detailsModal = document.getElementById('details-modal');
    const btnClose = document.querySelector('.btn-close');
    const deviceItems = document.querySelectorAll('.device-item');

    deviceItems.forEach(item => {
        item.addEventListener('click', (e) => {
            if (e.target.classList.contains('btn-block')) return;
            
            const name = item.querySelector('.device-name').textContent;
            document.getElementById('modal-device-name').textContent = `${name} Report`;
            detailsModal.classList.add('active');
            renderDetailCharts();
        });
    });

    btnClose.addEventListener('click', () => {
        detailsModal.classList.remove('active');
    });

    let detailChart, detailGraph;

    function renderDetailCharts() {
        // Circle Chart
        const circleCtx = document.getElementById('detailCircleChart').getContext('2d');
        if (detailChart) detailChart.destroy();
        detailChart = new Chart(circleCtx, {
            type: 'doughnut',
            data: {
                datasets: [{
                    data: [790, 51],
                    backgroundColor: ['#6750A4', '#FFC107'],
                    borderWidth: 0
                }]
            },
            options: {
                cutout: '80%',
                plugins: { legend: { display: false } }
            }
        });

        // Small History Graph
        const graphCtx = document.getElementById('detailGraph').getContext('2d');
        if (detailGraph) detailGraph.destroy();
        detailGraph = new Chart(graphCtx, {
            type: 'line',
            data: {
                labels: ['12:00', '16:00', '20:00'],
                datasets: [
                    {
                        data: [20, 60, 40],
                        borderColor: '#FFC107',
                        fill: true,
                        backgroundColor: 'rgba(255, 193, 7, 0.1)',
                        tension: 0.4,
                        borderWidth: 2,
                        pointRadius: 0
                    },
                    {
                        data: [50, 80, 70],
                        borderColor: '#6750A4',
                        fill: true,
                        backgroundColor: 'rgba(103, 80, 164, 0.1)',
                        tension: 0.4,
                        borderWidth: 2,
                        pointRadius: 0
                    }
                ]
            },
            options: {
                responsive: true,
                plugins: { legend: { display: false } },
                scales: {
                    x: { display: false },
                    y: { display: false }
                }
            }
        });
    }

    // Speed Simulation
    setInterval(() => {
        document.querySelectorAll('.speed-info').forEach(info => {
            if (info.style.opacity === '0.3') return;
            const down = (Math.random() * 500).toFixed(1);
            const up = (Math.random() * 100).toFixed(1);
            info.querySelector('.speed-down').textContent = `↓ ${down} KB/s`;
            info.querySelector('.speed-up').textContent = `↑ ${up} KB/s`;
        });
    }, 2000);
});
