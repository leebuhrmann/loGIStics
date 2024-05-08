class AlertService {
  static async getAllAlerts() {
    try {
      const response = await fetch("http://localhost:8081/api/alerts");
      if (!response.ok) {
        throw new Error("Failed to retrieve all alerts");
      }
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error:", error);
      throw error;
    }
  }

  static async getSubAlerts() {
    try {
      const response = await fetch(
        "http://localhost:8081/api/alerts/subscribed-boundaries"
      );
      if (!response.ok) {
        throw new Error("Failed to retrieve alerts in subscribed boundaries");
      }
      const data = await response.json();
      return data;
    } catch (error) {
      console.error("Error:", error);
      throw error;
    }
  }
}

export default AlertService;
