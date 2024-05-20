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
  createCheckboxAtom,
} from "@/state/atoms";

/**
 * SideCreationView component for creating a new boundary.
 * @returns {JSX.Element} The SideCreationView component.
 */
const SideCreationView = (): JSX.Element => {
  const setViewState = useSetRecoilState<string>(viewStateAtom);
  const [boundaryName, setBoundaryName] = useRecoilState(boundaryNameAtom);
  const [description, setDescription] = useRecoilState(boundaryDescriptionAtom);
  const [createCheckbox, setCreateCheckbox] = useRecoilState(createCheckboxAtom);
  const boundaryCoordinates = useRecoilValue(polygonCoordinatesAtom);
  /**
     * Handles the change event for the checkbox.
     * @public
     * @param {boolean} checked - The new checked state of the checkbox.
     */
  const handleCheckboxChange = (checked: boolean): void => {
    console.log("Checkbox checked:", checked);
    setCreateCheckbox(checked);
  };

  /**
  * Handles the save action, posting boundary data to the server.
  * @public
  * @returns {Promise<void>}
  */
  const handleSave = async (): Promise<void> => {
    const boundaryData = {
      name: boundaryName,
      description: description,
      subscribed: createCheckbox,
      the_geom: {
        type: "MultiPolygon",
        coordinates: [[boundaryCoordinates]],
      },
    };

    try {
      await BoundaryService.postBoundary(boundaryData);
      setViewState("info");
      setBoundaryName("");
      setDescription("");
    } catch (error) {
      console.error("Failed to save boundary data:", error);
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
        <Checkbox
          id="subsCheckbox"
          checked={createCheckbox}
          onCheckedChange={handleCheckboxChange}
        />
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
