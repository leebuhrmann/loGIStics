// TODO: validation to make sure there is user input
// TODO: something to let the user know they have saved, there are cool shadcn components for both of these

import React, { useState } from "react";
import SideFormViewCommon from "@/components/side-menu/side-form-view/side-form-view-common";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";

interface SideCreationViewProps {
  onClose: () => void;
  onClearPolygon?: () => void;
}

const SideCreationView: React.FC<SideCreationViewProps> = ({
  onClose,
  onClearPolygon,
}) => {
  // This makes React in control of the components instead of the DOM
  const [boundaryName, setBoundaryName] = useState("");
  const [description, setDescription] = useState("");
  
  const postBoundaryData = async () => {
    const boundaryData = {
      name: "placeholder",
      description: "placeholder",
      the_geom: {
        type: "MultiPolygon",
        coordinates: [
          [
            [
              [40.7168, -74.0060],
              [34.0522, -118.2437],
              [41.8781, -87.6298],
              [29.7604, -95.3698],
              [40.7128, -74.0060]
            ]
          ],
        ],
      },
    };
  
    const response = await fetch('http://localhost:8081/api/boundaries', {
      method: 'POST',
      //mode: "no-cors",
      headers: {
        'Content-Type': 'application/json',
        
      },
      body: JSON.stringify(boundaryData),
    });

    if (!response.ok) {
      throw new Error('Failed to save boundary data');
    }
  
    if (response.ok) {
      const responseData = await response.json();
      console.log('Boundary created:', responseData);
    } else {
      console.error('Failed to create boundary:', response.status, response.statusText);
    }
  };

  const handleSave = async() => {
  
  const postBoundaryData = async () => {
    const boundaryData = {
      name: "placeholder",
      description: "placeholder",
      the_geom: {
        type: "MultiPolygon",
        coordinates: [
          [
            [
              [40.7168, -74.0060],
              [34.0522, -118.2437],
              [23.8781, -87.6298],
              [30.7604, -95.3698],
              [40.7128, -74.0060]
            ]
          ],
        ],
      },
    };
  
    const response = await fetch('http://localhost:8081/api/boundaries', {
      method: 'POST',
      //mode: "no-cors",
      headers: {
        'Content-Type': 'application/json',
        
      },
      body: JSON.stringify(boundaryData),
    });

    if (!response.ok) {
      throw new Error('Failed to save boundary data');
    }
  
    if (response.ok) {
      const responseData = await response.json();
      console.log('Boundary created:', responseData);
    } else {
      console.error('Failed to create boundary:', response.status, response.statusText);
    }
  };

  const handleSave = async() => {
    // Placeholder for save logic
    console.log(
      "Saving boundary with name:",
      boundaryName,
      " and description:",
      description
    );
    await postBoundaryData();
    onClose();
    setBoundaryName("");
    setDescription("");
  };

  return (
    <div id="side-creation-view" className="w-full flex flex-col gap-2">
      <SideFormViewCommon
        onClose={onClose}
        isCreationView={true}
        onClearPolygon={onClearPolygon}
        title="Create New Boundary"
        boundaryName={boundaryName}
        setBoundaryName={setBoundaryName}
        description={description}
        setDescription={setDescription}
        boundaryPlaceholder="Enter Boundary Name"
        descriptionPlaceholder="Enter Description"
      />

      <div id="sub_checkbox" className="flex items-center space-x-2">
        <Checkbox id="subs" />
        <label
          htmlFor="subs"
          className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
        >
          Subscribe
        </label>
      </div>
      <Button onClick={handleSave}>Save</Button>
    </div>
  );
};

export default SideCreationView;
