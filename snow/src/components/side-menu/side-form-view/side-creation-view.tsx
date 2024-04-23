// TODO: validation to make sure there is user input
// TODO: something to let the user know they have saved, there are cool shadcn components for both of these

import React, { useState } from "react";
import SideFormViewCommon from "@/components/side-menu/side-form-view/side-form-view-common";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { useRecoilState, useSetRecoilState } from "recoil";
import {
  boundaryDescriptionAtom,
  boundaryNameAtom,
  viewStateAtom,
} from "@/state/atoms";

const SideCreationView = () => {
  const setViewState = useSetRecoilState<string>(viewStateAtom);
  const [boundaryName, setBoundaryName] = useRecoilState(boundaryNameAtom);
  const [description, setDescription] = useRecoilState(boundaryDescriptionAtom);

  const handleSave = () => {
    // Placeholder for save logic
    console.log(
      "Saving boundary with name:",
      boundaryName,
      " and description:",
      description
    );
    setViewState("info");
    setBoundaryName("");
    setDescription("");
  };

  return (
    <div id="side-creation-view" className="w-full flex flex-col gap-2">
      <SideFormViewCommon
        title="Create New Boundary"
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
      <Button onClick={handleSave} data-testid="side-creation-save-btn">
        Save
      </Button>
    </div>
  );
};

export default SideCreationView;
