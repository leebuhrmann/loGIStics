import { fireEvent, render, screen } from "@testing-library/react";
import "@testing-library/jest-dom";
import user from "@testing-library/user-event";
import SideCreationView from "@/components/side-menu/side-form-view/side-creation-view";
import SideFormViewCommon from "@/components/side-menu/side-form-view/side-form-view-common";
import { TextEncoder, TextDecoder } from "util";
import React from "react";
import SideInfoView from "@/components/side-menu/side-info-view/side-info-view";
import { RecoilRoot } from "recoil";
import {
  boundaryDescriptionAtom,
  boundaryNameAtom,
  clearPolygonAtom,
  viewStateAtom,
} from "@/state/atoms";
import { RecoilObserver } from "@/mock-data/RecoilObserver";

Object.assign(global, { TextDecoder, TextEncoder });

jest.mock("../components/map/MapComponent");
/* Not all functions are tested yet because the map component is crucial can cannot be mocked,
    like the clear functions

    Also, need to figure out how to handle check boxes because Shadcn defines them as buttons, and I haven't
    found any resources
    */
describe("SideCreationView", () => {
  let onViewChange: (value: any) => void,
    onNameChange: (value: any) => void,
    onDescriptionChange: (value: any) => void;

  beforeEach(() => {
    onViewChange = jest.fn();
    onNameChange = jest.fn();
    onDescriptionChange = jest.fn();

    user.setup();
    jest.mock("../components/side-menu/side-info-view/side-info-view", () => {
      return () => <SideInfoView />;
    });
    render(
      <RecoilRoot>
        <RecoilObserver
          node={viewStateAtom}
          onChange={onViewChange}
        ></RecoilObserver>
        <RecoilObserver
          node={boundaryNameAtom}
          onChange={onNameChange}
        ></RecoilObserver>
        <RecoilObserver
          node={boundaryDescriptionAtom}
          onChange={onDescriptionChange}
        ></RecoilObserver>
        <SideCreationView />
      </RecoilRoot>
    );
  });

  test("handleSave", async () => {
    const component = screen.getByTestId("side-creation-save-btn");
    fireEvent.click(component);
    expect(onViewChange).toHaveBeenCalledWith("info");
    expect(onNameChange).toHaveBeenCalledWith("");
    expect(onDescriptionChange).toHaveBeenCalledWith("");
  });
});

describe("SideFormViewCommon", () => {
  let onViewChange: (value: any) => void,
    onClearState: (value: any) => void,
    onNameChange: (value: any) => void,
    onDescriptionChange: (value: any) => void;

  beforeEach(() => {
    onViewChange = jest.fn();
    onClearState = jest.fn();
    onNameChange = jest.fn();
    onDescriptionChange = jest.fn();

    user.setup();
    jest.mock("../components/side-menu/side-info-view/side-info-view", () => {
      return () => <SideInfoView />;
    });
    render(
      <RecoilRoot>
        <RecoilObserver
          node={viewStateAtom}
          onChange={onViewChange}
        ></RecoilObserver>
        <RecoilObserver
          node={clearPolygonAtom}
          onChange={onClearState}
        ></RecoilObserver>
        <RecoilObserver
          node={boundaryNameAtom}
          onChange={onNameChange}
        ></RecoilObserver>
        <RecoilObserver
          node={boundaryDescriptionAtom}
          onChange={onDescriptionChange}
        ></RecoilObserver>
        <SideFormViewCommon
          title=""
          boundaryPlaceholder="Enter Boundary Name"
          descriptionPlaceholder="Enter Description"
        />{" "}
      </RecoilRoot>
    );
  });

  test("calls onClearPolygon and then onClose when isCreationView true", async () => {
    const component = screen.getByTestId("closeButton");
    fireEvent.click(component);

    expect(onViewChange).toHaveBeenCalledWith("info");
    expect(onClearState).toHaveBeenCalledWith(false);
  });
});
