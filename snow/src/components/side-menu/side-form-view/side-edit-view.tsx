import React, { useState } from "react";
import SideFormViewCommon from "@/components/side-menu/side-form-view/side-form-view-common";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";

interface SideEditViewProps {
  onClose: () => void;
  onClearPolygon?: () => void;
}

const SideEditView: React.FC<SideEditViewProps> = ({
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

  const handleDelete = () => {
    console.log("Deleting boundary");
    onClose();
  };

  return (
    <div id="side-edit-view" className="w-full flex flex-col gap-2">
      <SideFormViewCommon
        onClose={onClose}
        isCreationView={false}
        onClearPolygon={onClearPolygon}
        title="Edit Boundary"
        boundaryName={boundaryName}
        setBoundaryName={setBoundaryName}
        description={description}
        setDescription={setDescription}
        boundaryPlaceholder="Current Name"
        descriptionPlaceholder="Current Description"
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
      <Button variant="destructive" onClick={handleDelete}>
        Delete
      </Button>
    </div>
  );
};

export default SideEditView;
