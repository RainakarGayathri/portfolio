// Smooth scroll to sections
document.querySelectorAll('.navbar a').forEach(anchor => {
    anchor.addEventListener('click', function(e) {
      e.preventDefault();
      document.querySelector(this.getAttribute('href')).scrollIntoView({
        behavior: 'smooth'
      });
    });
  });
  
  // Show a simple alert when the contact button is clicked
  document.querySelector('.button').addEventListener('click', function() {
    alert('Thanks for reaching out! I will get back to you soon.');
  });
  