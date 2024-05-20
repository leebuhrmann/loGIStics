import React, { useState } from "react";
import SideFormViewCommon from "@/components/side-menu/side-form-view/side-form-view-common";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import {
  boundaryDescriptionAtom,
  boundaryNameAtom,
  viewStateAtom,
} from "@/state/atoms";
import { useRecoilState, useSetRecoilState } from "recoil";

/**
 * SideEditView component for editing an existing boundary.
 * @returns {JSX.Element} The SideEditView component.
 */
const SideEditView = (): JSX.Element => {
  const setViewState = useSetRecoilState(viewStateAtom);
  const [boundaryName, setBoundaryName] = useRecoilState(boundaryNameAtom);
  const [description, setDescription] = useRecoilState(boundaryDescriptionAtom);

  /**
   * Handles the save action.
   */
  const handleSave = () => {
    // Placeholder for save logic
    console.log(
      "Saving boundary with name:",
      boundaryName,
      " and description:",
      description
    );
    setBoundaryName("");
    setDescription("");
    setViewState("info");
  };

  /**
   * Handles the delete action.
   */
  const handleDelete = () => {
    console.log("Deleting boundary");
    setViewState("info");
  };

  return (
    <div id="side-edit-view" className="w-full flex flex-col gap-2">
      <SideFormViewCommon
        title="Edit Boundary"
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
