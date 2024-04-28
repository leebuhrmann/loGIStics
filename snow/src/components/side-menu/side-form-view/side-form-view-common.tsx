import React from "react";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Input } from "@/components/ui/input";
import { Cross1Icon } from "@radix-ui/react-icons";
import {
  boundaryDescriptionAtom,
  boundaryNameAtom,
  clearPolygonAtom,
  viewStateAtom,
} from "@/state/atoms";
import { useRecoilState, useSetRecoilState } from "recoil";

interface SideFormViewCommonProps {
  title: string;
  boundaryPlaceholder?: string;
  descriptionPlaceholder?: string;
}

const SideFormViewCommon: React.FC<SideFormViewCommonProps> = ({
  title,
  boundaryPlaceholder,
  descriptionPlaceholder,
}) => {
  const [viewState, setViewState] = useRecoilState<string>(viewStateAtom);
  const setClearState = useSetRecoilState(clearPolygonAtom);
  const [boundaryName, setBoundaryName] = useRecoilState(boundaryNameAtom);
  const [description, setDescription] = useRecoilState(boundaryDescriptionAtom);

  const handleClose = () => {
    if (viewState == "create") {
      setClearState(true);
    }
    setViewState("info" as string);
  };

  return (
    <div id="side-info-common">
      <div className="flex w-full items-center space-x-2">
        <h2 className="text-lg font-medium leading-none w-full">{title}</h2>
        <Button
          onClick={handleClose}
          variant="default_light"
          data-testid="closeButton"
        >
          <Cross1Icon />
        </Button>
      </div>
      <Input
        type="text"
        value={boundaryName}
        onChange={(e) => setBoundaryName(e.target.value)}
        placeholder={boundaryPlaceholder}
        className="w-full mt-4"
      />
      <Textarea
        value={description}
        onChange={(e) => setDescription(e.target.value)}
        placeholder={descriptionPlaceholder}
        className="w-full mt-4 h-48"
      />
    </div>
  );
};

export default SideFormViewCommon;
