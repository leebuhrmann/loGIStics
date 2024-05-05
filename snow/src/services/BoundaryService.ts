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

      if (!response.ok) {
        throw new Error(`Failed to create boundary: ${response.status} ${response.statusText}`);
      }
      return await response.json();
    } catch (error) {
      console.error('Error posting boundary:', error);
      throw error;
    }
  }

  static async getAllBoundaries() {
    try {
      const response = await fetch('http://localhost:8081/api/boundaries')
      if (!response.ok) {
        throw new Error(`HTTP error! status: ${response.status}`)
      }
      const boundaries = await response.json();
      return boundaries
    } catch (error) {
      console.error('From BoundaryService - Error fetching boundaries', error)
    }
  }

  static async updateBoundary(boundaryData: { id: any; }) {
    const response = await fetch(`http://localhost:8081/api/boundaries/${boundaryData.id}`, {
      method: 'PUT',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(boundaryData)
    });
    if (!response.ok) {
      throw new Error('Failed to update boundary');
    }
    return response.json();
  }
}



export default BoundaryService;