import { fetchData } from "@/services/ApiServices";
import React, { useState, useEffect } from "react";

function MyComponent() {
  const [data, setData] = useState<string | null>(null);

  useEffect(() => {
    fetchData()
      .then((response) => {
        setData(response);
      })
      .catch(console.error);
  }, []);

  return <div>{data ? <p>{data}</p> : <p></p>}</div>;
}

export default MyComponent;