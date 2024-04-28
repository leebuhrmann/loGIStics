// TODO: validation to make sure there is user input
// TODO: something to let the user know they have saved, there are cool shadcn components for both of these

import React, { useState } from "react";
import SideFormViewCommon from "@/components/side-menu/side-form-view/side-form-view-common";
import { Button } from "@/components/ui/button";
import { Checkbox } from "@/components/ui/checkbox";
import { useRecoilState, useSetRecoilState, useRecoilValue } from "recoil";
import BoundaryService from "@/services/BoundaryService";
import {
  boundaryDescriptionAtom,
  boundaryNameAtom,
  viewStateAtom,
  polygonCoordinatesAtom,
} from "@/state/atoms";

const SideCreationView = () => {
  const setViewState = useSetRecoilState<string>(viewStateAtom);
  const [boundaryName, setBoundaryName] = useRecoilState(boundaryNameAtom);
  const [description, setDescription] = useRecoilState(boundaryDescriptionAtom);
  const boundaryCoordinates = useRecoilValue(polygonCoordinatesAtom);



  const handleSave = async () => {
    const boundaryData = {
      name: boundaryName,
      description: description,
      the_geom: {
        type: "MultiPolygon",
        coordinates: [
          [
            boundaryCoordinates
          ]
        ]
      },
    };

    try {
      await BoundaryService.postBoundary(boundaryData);
      setViewState("info");
      setBoundaryName("");
      setDescription("");
    } catch (error) {
      console.error('Failed to save boundary data:', error);
    }
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
