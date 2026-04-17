// Dependent Dropdowns JavaScript for Registration Form

document.addEventListener('DOMContentLoaded', function() {
    const countrySelect = document.getElementById('country');
    const stateSelect = document.getElementById('state');
    const citySelect = document.getElementById('city');
    
    // Load countries on page load
    loadCountries();
    
    // Country change event
    countrySelect.addEventListener('change', function() {
        const countryId = this.value;
        
        // Reset state and city dropdowns
        resetDropdown(stateSelect, 'Select State');
        resetDropdown(citySelect, 'Select City');
        stateSelect.disabled = true;
        citySelect.disabled = true;
        
        if (countryId) {
            loadStates(countryId);
        }
    });
    
    // State change event
    stateSelect.addEventListener('change', function() {
        const stateId = this.value;
        
        // Reset city dropdown
        resetDropdown(citySelect, 'Select City');
        citySelect.disabled = true;
        
        if (stateId) {
            loadCities(stateId);
        }
    });
    
    // Load countries from API
    function loadCountries() {
        showLoading(countrySelect);
        
        fetch('/api/users/countries')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Countries data:', data); // Debug log
                countrySelect.innerHTML = '<option value="">Select Country</option>';
                
                if (data.status === "SUCCESS" && data.data && Array.isArray(data.data)) {
                    data.data.forEach(country => {
                        const option = document.createElement('option');
                        option.value = country.countryId;
                        option.textContent = country.countryName;
                        countrySelect.appendChild(option);
                    });
                } else {
                    console.error('Invalid countries data format:', data);
                    countrySelect.innerHTML = '<option value="">Error loading countries</option>';
                }
                
                countrySelect.disabled = false;
                hideLoading(countrySelect);
            })
            .catch(error => {
                console.error('Error loading countries:', error);
                countrySelect.innerHTML = '<option value="">Error loading countries</option>';
                hideLoading(countrySelect);
            });
    }
    
    // Load states based on country selection
    function loadStates(countryId) {
        showLoading(stateSelect);
        
        fetch(`/api/users/states/${countryId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('States data:', data); // Debug log
                stateSelect.innerHTML = '<option value="">Select State</option>';
                
                if (data.status === "SUCCESS" && data.data && Array.isArray(data.data)) {
                    data.data.forEach(state => {
                        const option = document.createElement('option');
                        option.value = state.stateId;
                        option.textContent = state.stateName;
                        stateSelect.appendChild(option);
                    });
                } else {
                    console.error('Invalid states data format:', data);
                    stateSelect.innerHTML = '<option value="">No states available</option>';
                }
                
                stateSelect.disabled = false;
                hideLoading(stateSelect);
            })
            .catch(error => {
                console.error('Error loading states:', error);
                stateSelect.innerHTML = '<option value="">Error loading states</option>';
                hideLoading(stateSelect);
            });
    }
    
    // Load cities based on state selection
    function loadCities(stateId) {
        showLoading(citySelect);
        
        fetch(`/api/users/cities/${stateId}`)
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log('Cities data:', data); // Debug log
                citySelect.innerHTML = '<option value="">Select City</option>';
                
                if (data.status === "SUCCESS" && data.data && Array.isArray(data.data)) {
                    data.data.forEach(city => {
                        const option = document.createElement('option');
                        option.value = city.cityId;
                        option.textContent = city.cityName;
                        citySelect.appendChild(option);
                    });
                } else {
                    console.error('Invalid cities data format:', data);
                    citySelect.innerHTML = '<option value="">No cities available</option>';
                }
                
                citySelect.disabled = false;
                hideLoading(citySelect);
            })
            .catch(error => {
                console.error('Error loading cities:', error);
                citySelect.innerHTML = '<option value="">Error loading cities</option>';
                hideLoading(citySelect);
            });
    }
    
    // Reset dropdown to default state
    function resetDropdown(selectElement, defaultText) {
        selectElement.innerHTML = `<option value="">${defaultText}</option>`;
        selectElement.value = '';
    }
    
    // Show loading state
    function showLoading(selectElement) {
        selectElement.classList.add('loading');
        selectElement.innerHTML = '<option value="">Loading...</option>';
    }
    
    // Hide loading state
    function hideLoading(selectElement) {
        selectElement.classList.remove('loading');
    }
    
    // Form validation
    const form = document.querySelector('form');
    if (form) {
        form.addEventListener('submit', function(e) {
            // Validate that all required fields are selected
            if (!countrySelect.value) {
                e.preventDefault();
                showError('Please select a country');
                countrySelect.focus();
                return;
            }
            
            if (!stateSelect.value) {
                e.preventDefault();
                showError('Please select a state');
                stateSelect.focus();
                return;
            }
            
            if (!citySelect.value) {
                e.preventDefault();
                showError('Please select a city');
                citySelect.focus();
                return;
            }
        });
    }
    
    // Show error message
    function showError(message) {
        // Remove existing error alerts
        const existingAlerts = document.querySelectorAll('.alert-danger');
        existingAlerts.forEach(alert => alert.remove());
        
        // Create new error alert
        const alertDiv = document.createElement('div');
        alertDiv.className = 'alert alert-danger alert-dismissible fade show';
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
        `;
        
        // Insert at the top of the card body
        const cardBody = document.querySelector('.card-body');
        if (cardBody) {
            cardBody.insertBefore(alertDiv, cardBody.firstChild);
        }
        
        // Auto dismiss after 5 seconds
        setTimeout(() => {
            if (alertDiv.parentNode) {
                alertDiv.remove();
            }
        }, 5000);
    }
    
    // Add smooth transitions
    function addSmoothTransitions() {
        const selects = document.querySelectorAll('select');
        selects.forEach(select => {
            select.style.transition = 'all 0.3s ease';
        });
    }
    
    addSmoothTransitions();
});

// Utility function to create a loading spinner
function createSpinner() {
    const spinner = document.createElement('span');
    spinner.className = 'spinner';
    spinner.innerHTML = '';
    return spinner;
}
