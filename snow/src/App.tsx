import React from 'react';
import MapComponent from './components/MapComponent';
import Hello from './components/fetchData';

function App() {
  return (
    <div>
      <h1>Open Layers Map</h1>
      <Hello />
      <MapComponent />
    </div>
  );
}
export default App;
