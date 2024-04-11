import { render, screen, waitFor } from "@testing-library/react";
import Home from "../app/page";
import "@testing-library/jest-dom";
jest.mock("../components/map/MapComponent");
import user from "@testing-library/user-event";
import SideEditView from "@/components/side-menu/side-form-view/side-edit-view";



    describe("Side edit view elements are rendered", () => {
        beforeEach(() => {
            user.setup();
            render(<Home />);
        });
        test("edit boundary text", () => {
            render(<SideEditView onClose={() => {}} />);
            const editBoundary = screen.getByText("Edit Boundary");
            expect(editBoundary).toBeInTheDocument();
        });

        test("subscribe checkbox", () => {
            render(<SideEditView onClose={() => {}} />);
            const checkbox = screen.getByLabelText("Subscribe");
            expect(checkbox).toBeInTheDocument();
        });

        test("save button", () => {
            render(<SideEditView onClose={() => {}} />);
            const saveButton = screen.getByRole("button", { name: /save/i });
            expect(saveButton).toBeInTheDocument();
        });

        test("delete button", () => {
            render(<SideEditView onClose={() => {}} />);
            const saveButton = screen.getByRole("button", { name: /delete/i });
            expect(saveButton).toBeInTheDocument();
        });


});
