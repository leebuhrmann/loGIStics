import React from "react";
import { Button } from "@/components/ui/button";
import { Textarea } from "@/components/ui/textarea";
import { Input } from "@/components/ui/input";
import { Cross1Icon } from "@radix-ui/react-icons";

interface SideFormViewCommonProps {
  onClose: () => void;
  title: string;
  boundaryName: string;
  setBoundaryName: (name: string) => void;
  description: string;
  setDescription: (description: string) => void;
  boundaryPlaceholder?: string;
  descriptionPlaceholder?: string;
  isCreationView?: boolean;
  onClearPolygon?: () => void;
}

const SideFormViewCommon: React.FC<SideFormViewCommonProps> = ({
  onClose,
  title,
  boundaryName,
  setBoundaryName,
  description,
  setDescription,
  boundaryPlaceholder,
  descriptionPlaceholder,
  isCreationView = false,
  onClearPolygon,
}) => {
  const handleClose = () => {
    if (isCreationView && onClearPolygon) {
      onClearPolygon();
      onClose();
    } else {
      onClose();
    }
  };

  return (
    <div id="side-info-common">
      <div className="flex w-full items-center space-x-2">
        <h2 className="text-lg font-medium leading-none w-full">{title}</h2>
        <Button onClick={handleClose} variant="default_light" data-testid="closeButton" >
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
