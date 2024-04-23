import { render, screen, waitFor } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
import user from "@testing-library/user-event";
import SideEditView from "@/components/side-menu/side-form-view/side-edit-view";
import { RecoilRoot } from "recoil";
import { TextEncoder, TextDecoder } from "util";

Object.assign(global, { TextDecoder, TextEncoder });
jest.mock("../components/map/MapComponent");

describe("Side edit view elements are rendered", () => {
  beforeEach(() => {
    user.setup();
    render(
      <RecoilRoot>
        <SideEditView />
      </RecoilRoot>
    );
  });
  test("edit boundary text", () => {
    const editBoundary = screen.getByText("Edit Boundary");
    expect(editBoundary).toBeInTheDocument();
  });

  test("subscribe checkbox", () => {
    const checkbox = screen.getByLabelText("Subscribe");
    expect(checkbox).toBeInTheDocument();
  });

  test("save button", () => {
    const saveButton = screen.getByRole("button", { name: /save/i });
    expect(saveButton).toBeInTheDocument();
  });

  test("delete button", () => {
    const saveButton = screen.getByRole("button", { name: /delete/i });
    expect(saveButton).toBeInTheDocument();
  });
});
