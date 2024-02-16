const BASE_URL = "http://localhost:8081";
export const fetchData = async () => {
  try {
    const response = await fetch(`${BASE_URL}/hello`);
    if (!response.ok) throw new Error("Network response was not ok");
    return await response.text();
  } catch (error) {
    console.error("Fetch error: ", error);
    throw error;
  }
};
