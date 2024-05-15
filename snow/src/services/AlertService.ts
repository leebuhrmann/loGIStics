/**
 * Service for fetching alert data from the backend.
 */
class AlertService {

  /**
   * Gets all alerts from the backend.
   * @returns A list containing all alerts.
   */
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

  /**
   * Gets all alerts that fall within subscribed boundaries from the backend.
   * @returns A list containing all alerts that fall within subscribed boundaries.
   */
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
