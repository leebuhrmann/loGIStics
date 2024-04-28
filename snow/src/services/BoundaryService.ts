class BoundaryService {
    static async postBoundary(boundaryData: any) {
      try {
        const response = await fetch('http://localhost:8081/api/boundaries', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(boundaryData),
        });
  
        if (response.ok) {
          const responseData = await response.json();
          console.log('Boundary created:', responseData);
          return responseData;
        } else {
          console.error('Failed to create boundary:', response.status, response.statusText);
        }
      } catch (error) {
        console.error('Error posting boundary:', error);
        throw error;
      }
    }
  }
  
  export default BoundaryService;