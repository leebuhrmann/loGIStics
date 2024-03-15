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

  const handleSave = () => {
    // Placeholder for save logic
    console.log(
      "Saving boundary with name:",
      boundaryName,
      " and description:",
      description
    );
    onClose();
    setBoundaryName("");
    setDescription("");
  };

  return (
    <div id="side-creation-view" className="w-full">
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

      <div className="mt-4 flex justify-start items-center space-x-2">
        <Button onClick={handleSave}>Save</Button>
        <div id="sub_checkbox" className="flex items-center space-x-2">
          <Checkbox id="subs" />
          <label
            htmlFor="subs"
            className="text-sm font-medium leading-none peer-disabled:cursor-not-allowed peer-disabled:opacity-70"
          >
            Subscribe
          </label>
        </div>
      </div>
    </div>
  );
};

export default SideCreationView;
